package io.github.alancavalcante_dev.codefreelaapi.presentation.controller;

import io.github.alancavalcante_dev.codefreelaapi.domain.appointment.AppointmentService;
import io.github.alancavalcante_dev.codefreelaapi.domain.container.ContainerService;
import io.github.alancavalcante_dev.codefreelaapi.domain.entity.Container;
import io.github.alancavalcante_dev.codefreelaapi.domain.entity.Appointment;
import io.github.alancavalcante_dev.codefreelaapi.domain.entity.Profile;
import io.github.alancavalcante_dev.codefreelaapi.domain.entity.User;
import io.github.alancavalcante_dev.codefreelaapi.domain.generatedcommentia.GeneratorCommentIA;
import io.github.alancavalcante_dev.codefreelaapi.domain.notification.NotificationEmailSender;
import io.github.alancavalcante_dev.codefreelaapi.domain.profile.ProfileService;
import io.github.alancavalcante_dev.codefreelaapi.infrastructure.security.UserLogged;
import io.github.alancavalcante_dev.codefreelaapi.presentation.dto.appointment.AppointmentsRequests;
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

@Slf4j
@RestController
@RequestMapping("api/user/client/project")
@Schema(name = "Marcação de ponto do projeto")
@Tag(name = "Marcação de ponto do projeto")
@RequiredArgsConstructor
public class AppointmentController {

    private final ContainerService containerService;
    private final AppointmentService appointmentService;
    private final GeneratorCommentIA generatorCommentIA;
    private final NotificationEmailSender notification;
    private final ProfileService profileService;
    private final UserLogged logged;

    @GetMapping("{id}/appointment")
    @PreAuthorize("hasRole('USER')")
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
    @PreAuthorize("hasRole('USER')")
    @Operation(summary = "Todas as marcações relacionada ao projeto consultado - Cliente")
    public ResponseEntity<Appointment> getAppointmentDetails(
            @PathVariable("id") String idProject,
            @PathVariable("idAppointment") UUID idAppointment,
            @PathParam("withCommentIA") boolean withCommentIA
    ) {
        List<Container> containersProjectByUser = containerService.getContainersByUserClient(logged.load())
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
        if (withCommentIA) {
            String commentGenerated = generatorCommentIA.generate(appointment);
            appointment.setCommentsGeneratedIA(commentGenerated);
            appointment = appointmentService.saveWorkSession(appointment);
            senderEmailComments();
        }

        return ResponseEntity.ok(appointment);
    }


    public void senderEmailComments() {
        String htmlText = "Comentário da IA gerada<br><br>Clique no link ao lado para visualizar: (link)";

        Profile profile = profileService.getProfileByIdUser(logged.load())
                .orElseThrow(() -> new RuntimeException("Perfil não encontrado"));

        notification.send(profile.getEmail(), "CodeFreela - Comentário da IA gerada", htmlText);
    }

}
