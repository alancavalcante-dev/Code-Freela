package io.github.alancavalcante_dev.araraflyapi.domain.match.controller;

import io.github.alancavalcante_dev.araraflyapi.domain.match.entity.ProjectBusiness;
import io.github.alancavalcante_dev.araraflyapi.domain.match.service.ProjectBusinessService;
import io.github.alancavalcante_dev.araraflyapi.infrastructure.security.UserLogged;
import io.github.alancavalcante_dev.araraflyapi.domain.match.dto.ProjectBusinessConfirmationDTO;
import io.github.alancavalcante_dev.araraflyapi.domain.match.dto.ProjectBusinessResponseClientDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;


@RestController
@RequestMapping("api/client/projects/business")
@RequiredArgsConstructor
@Tag(name = "Negociação de projetos - Match de clients")
public class ProjectBusinessClientController {


    private final ProjectBusinessService service;
    private final UserLogged logged;


    @GetMapping
    @PreAuthorize("hasRole('CLIENT')")
    @Operation(summary = "Pega todos as negociacoes ou matches do client")
    public ResponseEntity<List<ProjectBusinessResponseClientDTO>> getAllMatchesBusinessUserClient() {
        List<ProjectBusiness> listProjects = service.getAllMatchesBusinessUserClient(logged.load());
        if (listProjects.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        List<ProjectBusinessResponseClientDTO> dto = listProjects
                .stream().map(p -> new ProjectBusinessResponseClientDTO(
                        p.getIdProjectBusiness().toString(),
                        p.getProject().getTitle(),
                        p.getProject().getDescription(),
                        p.isConfirmClient(),
                        p.isConfirmDeveloper(),
                        p.getUserDeveloper().getId().toString(),
                        p.getProject().getClosingDate(),
                        p.getDateMatch())
                ).toList();

        return ResponseEntity.ok(dto);
    }


    @PutMapping("{id}")
    @PreAuthorize("hasRole('CLIENT')")
    @Operation(summary = "Altera o match")
    public ResponseEntity<Void> updateMatch(
            @PathVariable String id,
            @RequestBody @Valid ProjectBusinessConfirmationDTO request
    ) {
        Optional<ProjectBusiness> projectOpt = service.getByIdProjectBusinessForUserClient(logged.load().getId(), UUID.fromString(id));

        if (projectOpt.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        ProjectBusiness proj = projectOpt.get();
        proj.setConfirmClient(request.isConfirmation());
        service.update(proj);
        return ResponseEntity.ok().build();
    }

}