package io.github.alancavalcante_dev.codefreelaapi.domain.appointment.controller;

import io.github.alancavalcante_dev.codefreelaapi.domain.container.service.ContainerService;
import io.github.alancavalcante_dev.codefreelaapi.domain.appointment.service.AppointmentService;
import io.github.alancavalcante_dev.codefreelaapi.domain.container.entity.Container;
import io.github.alancavalcante_dev.codefreelaapi.domain.appointment.entity.Appointment;
import io.github.alancavalcante_dev.codefreelaapi.domain.entity.enums.StateProject;
import io.github.alancavalcante_dev.codefreelaapi.infrastructure.security.UserLogged;
import io.github.alancavalcante_dev.codefreelaapi.domain.appointment.dto.InsertAppointmentRequest;
import io.github.alancavalcante_dev.codefreelaapi.domain.appointment.dto.UpdateCommentRequest;
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
@RequestMapping("api/user/developer/project")
@Schema(name = "Marcação de ponto do projeto")
@Tag(name = "Marcação de ponto do projeto")
@RequiredArgsConstructor
public class AppointmentDeveloperController {


    private final ContainerService containerService;
    private final AppointmentService appointmentService;
    private final UserLogged logged;


    @GetMapping("{id}/appointment")
    @PreAuthorize("hasRole('USER')")
    @Operation(summary = "Todas as marcações relacionada ao projeto consultado")
    public ResponseEntity<List<Appointment>> getAllAppointmentByProject(@PathVariable("id") String idProject) {

        List<Container> containersProjectByUser = containerService.getContainersByUserDeveloper(logged.load())
                .stream()
                .filter(c -> c.getStateProject() == StateProject.WORKING)
                .filter(c -> c.getProjectBusiness().getProject().getIdProject().equals(UUID.fromString(idProject)))
                .toList();

        if (containersProjectByUser.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        List<Appointment> appointmentProject = containersProjectByUser.getFirst().getAppointments();
        return ResponseEntity.ok(appointmentProject);
    }


    @PostMapping("{id}/appointment")
    @PreAuthorize("hasRole('USER')")
    @Operation(summary = "Registra uma marcação no projeto selecionado")
    @Transactional
    public ResponseEntity<Appointment> postAppointment(@PathVariable("id") String idProject, @RequestBody InsertAppointmentRequest dto) {

        List<Container> containersProjectByUser = containerService.getContainersByUserDeveloper(logged.load())
                .stream()
                .filter(c -> c.getProjectBusiness().getProject().getIdProject().equals(UUID.fromString(idProject)))
                .toList();

        if (containersProjectByUser.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        Container container = containersProjectByUser.getFirst();
        container.setStateProject(StateProject.WORKING);

        Appointment appointment = new Appointment();
        appointment.setContainer(container);
        appointment.setComment(dto.comment());
        appointment.setDateStarting(dto.dateStarting());
        appointment.setDateClosing(dto.dateClosing());
        appointment.setUser(logged.load());

        Appointment workSaved = appointmentService.saveWorkSession(appointment);

        return ResponseEntity.ok(workSaved);
    }

    @PatchMapping("{id}/appointment/{idAppointment}/comment")
    @PreAuthorize("hasRole('USER')")
    @Operation(summary = "Atualiza apenas o comentário de uma marcação específica")
    @Transactional
    public ResponseEntity<Appointment> updateComment(
            @PathVariable("id") String idProject,
            @PathVariable("idAppointment") UUID idWorkSession,
            @RequestBody UpdateCommentRequest request
    ) {
        List<Container> containersProjectByUser = containerService.getContainersByUserDeveloper(logged.load())
                .stream()
                .filter(c -> c.getStateProject() == StateProject.WORKING)
                .filter(c -> c.getProjectBusiness().getProject().getIdProject().equals(UUID.fromString(idProject)))
                .toList();

        if (containersProjectByUser.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        Container container = containersProjectByUser.getFirst();
        List<Appointment> appointments = container.getAppointments();

        if (appointments.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        Appointment appointment = appointments.stream()
                .filter(a -> a.getIdAppointment().equals(idWorkSession))
                .findFirst()
                .orElse(null);

        if (appointment == null) {
            return ResponseEntity.notFound().build();
        }

        appointment.setComment(request.comment());
        Appointment updated = appointmentService.saveWorkSession(appointment);

        return ResponseEntity.ok(updated);
    }

}
