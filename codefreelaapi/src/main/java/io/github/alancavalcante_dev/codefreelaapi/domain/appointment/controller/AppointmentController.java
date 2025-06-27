package io.github.alancavalcante_dev.codefreelaapi.domain.appointment.controller;

import io.github.alancavalcante_dev.codefreelaapi.domain.appointment.service.AppointmentService;
import io.github.alancavalcante_dev.codefreelaapi.domain.container.service.ContainerService;
import io.github.alancavalcante_dev.codefreelaapi.domain.container.entity.Container;
import io.github.alancavalcante_dev.codefreelaapi.domain.appointment.entity.Appointment;
import io.github.alancavalcante_dev.codefreelaapi.domain.entity.User;
import io.github.alancavalcante_dev.codefreelaapi.domain.profile.entity.Profile;
import io.github.alancavalcante_dev.codefreelaapi.infrastructure.artificialintelligence.GeneratorCommentIA;
import io.github.alancavalcante_dev.codefreelaapi.commom.notification.NotificationEmailSender;
import io.github.alancavalcante_dev.codefreelaapi.domain.profile.service.ProfileService;
import io.github.alancavalcante_dev.codefreelaapi.infrastructure.security.UserLogged;
import io.github.alancavalcante_dev.codefreelaapi.domain.appointment.dto.AppointmentsRequests;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.websocket.server.PathParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;
import java.util.concurrent.ExecutionException;

@Slf4j
@RestController
@RequestMapping("api/client/project")
@Schema(name = "Marcação de ponto do projeto - Cliente")
@Tag(name = "Marcação de ponto do projeto - Cliente")
@RequiredArgsConstructor
public class AppointmentController {

    private final ContainerService containerService;
    private final AppointmentService appointmentService;
    private final NotificationEmailSender notification;
    private final GeneratorCommentIA generatorCommentIA;
    private final UserLogged logged;

    @GetMapping("{id}/appointment")
    @PreAuthorize("hasRole('CLIENT')")
    @Operation(summary = "Todas as marcações relacionada ao projeto consultado - Cliente")
    public ResponseEntity<List<AppointmentsRequests>> getAllAppointmentByProject(@PathVariable("id") String idProject) {

        List<Container> containersProjectByUser = containerService.getContainersByUserClient(logged.load())
                .stream()
                .filter(c -> c.getProjectBusiness().getProject().getIdProject().equals(UUID.fromString(idProject)))
                .toList();

        if (containersProjectByUser.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        List<Appointment> appointmentProject = containersProjectByUser.getFirst().getAppointments();
        List<AppointmentsRequests> requests = appointmentProject.stream()
                .map(a -> new AppointmentsRequests(a.getIdAppointment() ,a.getDateCreated())).toList();

        return ResponseEntity.ok(requests);
    }


    @GetMapping("{id}/appointment/{idAppointment}/details")
    @PreAuthorize("hasRole('CLIENT')")
    @Operation(summary = "Todas as marcações relacionada ao projeto consultado - Cliente")
    public ResponseEntity<Appointment> getAppointmentDetails(
            @PathVariable("id") String idProject,
            @PathVariable("idAppointment") UUID idAppointment,
            @PathParam("withCommentIA") boolean withCommentIA
    ) throws ExecutionException, InterruptedException {
        User user = logged.load();
        List<Container> containersProjectByUser = containerService.getContainersByUserClient(user)
                .stream()
                .filter(c -> c.getProjectBusiness().getProject().getIdProject().equals(UUID.fromString(idProject)))
                .toList();

        if (containersProjectByUser.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        List<Appointment> appointmentProject = containersProjectByUser.getFirst().getAppointments();
        List<Appointment> appointmentDetails = appointmentProject.stream().filter(a -> a.getIdAppointment().equals(idAppointment)).toList();
        if (appointmentDetails.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        Appointment appointment = appointmentDetails.getFirst();
        if (withCommentIA && appointment.getCommentsGeneratedIA() == null) {
            generatorComment(appointment, user.getProfile().getEmail());
        }

        return ResponseEntity.ok(appointment);
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
