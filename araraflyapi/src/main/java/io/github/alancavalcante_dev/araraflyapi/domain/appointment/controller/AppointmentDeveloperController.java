package io.github.alancavalcante_dev.araraflyapi.domain.appointment.controller;

import io.github.alancavalcante_dev.araraflyapi.commom.notification.NotificationEmailSender;
import io.github.alancavalcante_dev.araraflyapi.domain.container.service.ContainerService;
import io.github.alancavalcante_dev.araraflyapi.domain.appointment.service.AppointmentService;
import io.github.alancavalcante_dev.araraflyapi.domain.container.entity.Container;
import io.github.alancavalcante_dev.araraflyapi.domain.appointment.entity.Appointment;
import io.github.alancavalcante_dev.araraflyapi.domain.entity.User;
import io.github.alancavalcante_dev.araraflyapi.domain.entity.enums.StateProject;
import io.github.alancavalcante_dev.araraflyapi.infrastructure.artificialintelligence.GeneratorCommentIA;
import io.github.alancavalcante_dev.araraflyapi.infrastructure.security.UserLogged;
import io.github.alancavalcante_dev.araraflyapi.domain.appointment.dto.InsertAppointmentRequest;
import io.github.alancavalcante_dev.araraflyapi.domain.appointment.dto.UpdateCommentRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.transaction.Transactional;
import jakarta.websocket.server.PathParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;


@Slf4j
@RestController
@RequestMapping("api/developer/project")
@Schema(name = "Marcação de ponto do projeto")
@Tag(name = "Marcação de ponto do projeto")
@RequiredArgsConstructor
public class AppointmentDeveloperController {


    private final ContainerService containerService;
    private final AppointmentService appointmentService;
    private final NotificationEmailSender notification;
    private final GeneratorCommentIA generatorCommentIA;
    private final UserLogged logged;


    @GetMapping("{id}/appointment")
    @PreAuthorize("hasRole('DEVELOPER')")
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
    @PreAuthorize("hasRole('DEVELOPER')")
    @Operation(summary = "Registra uma marcação no projeto selecionado")
    @Transactional
    public ResponseEntity<Appointment> postAppointment(
            @PathVariable("id") String idProject,
            @RequestBody InsertAppointmentRequest dto,
            @PathParam("withCommentIA") boolean withCommentIA
    ) {
        User user = logged.load();
        List<Container> containersProjectByUser = containerService.getContainersByUserDeveloper(user)
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
        log.info("Marcação salva");

        if (withCommentIA) {
            generatorComment(appointment, user.getProfile().getEmail());
        }

        return ResponseEntity.ok(workSaved);
    }

    @PatchMapping("{id}/appointment/{idAppointment}/comment")
    @PreAuthorize("hasRole('DEVELOPER')")
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


    public void generatorComment(Appointment appointment, String email) {
        generatorCommentIA.generate(appointment)
                .thenApply(comment -> {
                    appointment.setCommentsGeneratedIA(comment);
                    return appointmentService.saveWorkSession(appointment);
                })
                .thenAccept(savedAppointment -> {
                    senderEmailComments(email); // passe o appointment
                })
                .exceptionally(ex -> {
                    log.error("Erro ao gerar comentário IA e/ou enviar email", ex);
                    return null;
                });
    }


    public void senderEmailComments(String email) {
        String htmlText = "Comentário da IA gerada<br><br>Clique no link ao lado para visualizar: (link)";

        notification.send(email, "Arara Fly - Comentário da IA gerada", htmlText);
        log.info("E-mail enviado do comentário por IA gerada");
    }




}
