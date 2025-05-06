package io.github.alancavalcante_dev.codefreelaapi.presentation.controller;

import io.github.alancavalcante_dev.codefreelaapi.domain.container.ContainerService;
import io.github.alancavalcante_dev.codefreelaapi.domain.entity.Container;
import io.github.alancavalcante_dev.codefreelaapi.infrastructure.security.UserLogged;
import io.github.alancavalcante_dev.codefreelaapi.presentation.dto.container.ContainerDTO;
import io.github.alancavalcante_dev.codefreelaapi.presentation.dto.container.ContainerDetailsDTO;
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
@RequestMapping("api/user/projects/business/clients")
@RequiredArgsConstructor
@Tag(name = "Negociação de projetos - Match de clients")
public class ContainerController {


    private final ContainerService service;
    private final UserLogged logged;


    @GetMapping
    @PreAuthorize("hasRole('USER')")
    @Operation(summary = "Pega todos os containers do desenvolvedor logado")
    public ResponseEntity<List<ContainerDTO>> getContainersByUser() {
        List<Container> listContainers = service.getContainersByUser(logged.load());

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
    @PreAuthorize("hasRole('USER')")
    @Operation(summary = "Consulta Container por ID com usuário logado")
    public ResponseEntity<ContainerDetailsDTO> getContainerByUserAndIdContainer(@PathVariable String id) {
        Optional<Container> containerOpt = service.getContainerById(logged.load().getId(), UUID.fromString(id));

        if (containerOpt.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        Container container = containerOpt.get();
        ContainerDetailsDTO containerDetails = new ContainerDetailsDTO(
                container.getIdContainer().toString(),
                container.getProjectBusiness().getProject().getTitle(),
                container.getProjectBusiness().getProject().getDescription(),
                container.getProjectBusiness().getProject().getStateBusiness(),
                container.getProjectBusiness().getProject().getClosingDate(),
                container.getDateCreated(),
                container.getStateProject(),
                container.getDateLastDeploy()
        );

        return ResponseEntity.ok(containerDetails);
    }

}