package io.github.alancavalcante_dev.codefreelaapi.domain.container.controller;

import io.github.alancavalcante_dev.codefreelaapi.domain.appointment.dto.AppointmentDate;
import io.github.alancavalcante_dev.codefreelaapi.domain.appointment.entity.Appointment;
import io.github.alancavalcante_dev.codefreelaapi.domain.container.service.ContainerService;
import io.github.alancavalcante_dev.codefreelaapi.domain.container.entity.Container;
import io.github.alancavalcante_dev.codefreelaapi.domain.entity.User;
import io.github.alancavalcante_dev.codefreelaapi.domain.match.entity.ProjectBusiness;
import io.github.alancavalcante_dev.codefreelaapi.domain.profile.dto.ProfileDataSimpleDTO;
import io.github.alancavalcante_dev.codefreelaapi.domain.profile.entity.Profile;
import io.github.alancavalcante_dev.codefreelaapi.domain.profile.service.ProfileService;
import io.github.alancavalcante_dev.codefreelaapi.domain.project.entity.Project;
import io.github.alancavalcante_dev.codefreelaapi.infrastructure.security.UserLogged;
import io.github.alancavalcante_dev.codefreelaapi.domain.container.dto.ContainerDTO;
import io.github.alancavalcante_dev.codefreelaapi.domain.container.dto.ContainerDetailsDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;
import java.util.UUID;


@RestController
@RequestMapping("api/developer/project/container")
@RequiredArgsConstructor
@Tag(name = "Container dos projetos")
public class ContainerController {


    private final ContainerService service;
    private final ProfileService profileService;
    private final UserLogged logged;


    @GetMapping
    @PreAuthorize("hasRole('DEVELOPER')")
    @Operation(summary = "Pega todos os containers do desenvolvedor logado")
    public ResponseEntity<List<ContainerDTO>> getContainersByUser() {
        List<Container> listContainers = service.getContainersByUserDeveloper(logged.load());

        if (listContainers.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        List<ContainerDTO> dto = listContainers
                .stream().map(c -> new ContainerDTO(
                        c.getIdContainer().toString(),
                        c.getProjectBusiness().getProject().getTitle(),
                        c.getProjectBusiness().getProject().getDescription(),
                        c.getProjectBusiness().getProject().getClosingDate()
                )).toList();

        return ResponseEntity.ok(dto);
    }


    @GetMapping("{id}")
    @PreAuthorize("hasRole('DEVELOPER')")
    @Operation(summary = "Consulta Container por ID com usuário logado")
    public ResponseEntity<ContainerDetailsDTO> getContainerByUserAndIdContainer(@PathVariable String id) {
        Optional<Container> containerOpt = service.getContainerById(logged.load().getId(), UUID.fromString(id));

        if (containerOpt.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        Container container = containerOpt.get();

        List<Appointment> appointments = container.getAppointments();

        String comment = appointments.isEmpty() ? null : appointments.getLast().getComment();

        List<AppointmentDate> appointmentList = appointments
                .stream().map(a -> new AppointmentDate(a.getDateStarting(), a.getDateClosing())).toList();


        ProjectBusiness match = container.getProjectBusiness();
        Project project = match.getProject();

        User client = project.getUser();
        User developer = match.getUserDeveloper();

        ProfileDataSimpleDTO profileClient = profileService.getProfileByIdUser(client)
                .map(c -> new ProfileDataSimpleDTO(c.getName(), c.getEmail(), c.getDateCreated()))
                .orElseThrow();

        ProfileDataSimpleDTO profileDeveloper = profileService.getProfileByIdUser(developer)
                .map(c -> new ProfileDataSimpleDTO(c.getName(), c.getEmail(), c.getDateCreated()))
                .orElseThrow();

        ContainerDetailsDTO containerDetails = new ContainerDetailsDTO(
                container.getIdContainer().toString(),
                project.getTitle(),
                project.getDescription(),
                project.getStateBusiness(),
                project.getPriceExists(),
                project.getClosingDate(),

                container.getDateCreated(),
                container.getStateProject(),
                appointmentList,
                comment,

                profileClient,
                profileDeveloper


        );

        return ResponseEntity.ok(containerDetails);
    }

}