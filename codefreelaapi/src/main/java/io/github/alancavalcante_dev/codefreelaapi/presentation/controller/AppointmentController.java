package io.github.alancavalcante_dev.codefreelaapi.presentation.controller;

import io.github.alancavalcante_dev.codefreelaapi.domain.container.ContainerService;
import io.github.alancavalcante_dev.codefreelaapi.domain.container.WorkSessionService;
import io.github.alancavalcante_dev.codefreelaapi.domain.entity.Container;
import io.github.alancavalcante_dev.codefreelaapi.domain.entity.WorkSession;
import io.github.alancavalcante_dev.codefreelaapi.domain.entity.enums.StateProject;
import io.github.alancavalcante_dev.codefreelaapi.infrastructure.repository.ContainerRepository;
import io.github.alancavalcante_dev.codefreelaapi.infrastructure.security.UserLogged;
import io.github.alancavalcante_dev.codefreelaapi.presentation.dto.appointment.UpdateCommentRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("api/user/project")
@Schema(name = "Marcação de ponto do projeto")
@Tag(name = "Marcação de ponto do projeto")
@RequiredArgsConstructor
public class AppointmentController {

    private final ContainerService containerService;
    private final WorkSessionService workSessionService;
    private final UserLogged logged;

    @GetMapping("{id}/appointment")
    @PreAuthorize("hasRole('USER')")
    @Operation(summary = "Todas as marcações relacionada ao projeto consultado")
    public ResponseEntity<List<WorkSession>> getAllAppointmentByProject(@PathVariable("id") String idProject) {

        List<Container> containersProjectByUser = containerService.getContainersByUser(logged.load())
                .stream()
                .filter(c -> c.getStateProject() == StateProject.WORKING)
                .filter(c -> c.getProjectBusiness().getProject().getIdProject().equals(UUID.fromString(idProject)))
                .toList();

        if (containersProjectByUser.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        List<WorkSession> appointmentProject = containersProjectByUser.getFirst().getWorkSessions();
        return ResponseEntity.ok(appointmentProject);
    }


    @PostMapping("{id}/appointment")
    @PreAuthorize("hasRole('USER')")
    @Operation(summary = "Registra uma marcação no projeto selecionado")
    @Transactional
    public ResponseEntity<WorkSession> postAppointment(@PathVariable("id") String idProject, @RequestBody WorkSessionRequest dto) {

        List<Container> containersProjectByUser = containerService.getContainersByUser(logged.load())
                .stream()
                .filter(c -> c.getProjectBusiness().getProject().getIdProject().equals(UUID.fromString(idProject)))
                .toList();

        if (containersProjectByUser.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        Container container = containersProjectByUser.getFirst();
        container.setStateProject(StateProject.WORKING);

        WorkSession appointment = new WorkSession();
        appointment.setContainer(container);
        appointment.setComment(dto.comment());
        appointment.setDateStarting(dto.dateStarting());
        appointment.setDateClosing(dto.dateClosing());
        appointment.setUser(logged.load());

        WorkSession workSaved = workSessionService.saveWorkSession(appointment);

        return ResponseEntity.ok(workSaved);
    }

    @PatchMapping("{id}/appointment/{idAppointment}/comment")
    @PreAuthorize("hasRole('USER')")
    @Operation(summary = "Atualiza apenas o comentário de uma marcação específica")
    @Transactional
    public ResponseEntity<WorkSession> updateComment(
            @PathVariable("id") String idProject,
            @PathVariable("idAppointment") UUID idWorkSession,
            @RequestBody UpdateCommentRequest request
    ) {
        List<Container> containersProjectByUser = containerService.getContainersByUser(logged.load())
                .stream()
                .filter(c -> c.getStateProject() == StateProject.WORKING)
                .filter(c -> c.getProjectBusiness().getProject().getIdProject().equals(UUID.fromString(idProject)))
                .toList();

        if (containersProjectByUser.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        Container container = containersProjectByUser.getFirst();
        List<WorkSession> appointments = container.getWorkSessions();

        if (appointments.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        WorkSession workSession = appointments.stream()
                .filter(a -> a.getIdWorkSession().equals(idWorkSession))
                .findFirst()
                .orElse(null);

        if (workSession == null) {
            return ResponseEntity.notFound().build();
        }

        workSession.setComment(request.comment());
        WorkSession updated = workSessionService.saveWorkSession(workSession);

        return ResponseEntity.ok(updated);
    }

}
