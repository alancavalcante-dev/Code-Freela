package io.github.alancavalcante_dev.codefreelaapi.presentation.controller;

import io.github.alancavalcante_dev.codefreelaapi.domain.entity.Profile;
import io.github.alancavalcante_dev.codefreelaapi.domain.entity.Project;
import io.github.alancavalcante_dev.codefreelaapi.domain.entity.ProjectBusiness;
import io.github.alancavalcante_dev.codefreelaapi.domain.project.ProjectService;
import io.github.alancavalcante_dev.codefreelaapi.domain.projectbusiness.ProjectBusinessService;
import io.github.alancavalcante_dev.codefreelaapi.infrastructure.security.UserLogged;
import io.github.alancavalcante_dev.codefreelaapi.presentation.dto.profile.ProfileResponseDTO;
import io.github.alancavalcante_dev.codefreelaapi.presentation.dto.profile.ProfileUpdateRequestDTO;
import io.github.alancavalcante_dev.codefreelaapi.presentation.dto.projectbusiness.ProjectBusinessConfirmationDTO;
import io.github.alancavalcante_dev.codefreelaapi.presentation.dto.projectbusiness.ProjectBusinessDTO;
import io.github.alancavalcante_dev.codefreelaapi.presentation.dto.projectbusiness.ProjectBusinessResponseDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;


@RestController
@RequestMapping("api/user/projects/business")
@RequiredArgsConstructor
@Tag(name = "Negociação de projetos - Match de developer")
public class ProjectBusinessController {


    private final ProjectBusinessService service;
    private final ProjectService projectService;
    private final UserLogged logged;


    @GetMapping
    @PreAuthorize("hasRole('USER')")
    @Operation(summary = "Pega todos as negociacoes ou matches do cliente")
    public ResponseEntity<List<ProjectBusinessResponseDTO>> getAllMatchesBusiness() {
        List<ProjectBusiness> listProjects = service.getAllMatchesBusiness(logged.load());
        if (listProjects.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        List<ProjectBusinessResponseDTO> dto = listProjects
                .stream().map(p -> new ProjectBusinessResponseDTO(
                        p.getIdProjectBusiness().toString(),
                        p.getProject().getTitle(),
                        p.getProject().getDescription(),
                        p.isConfirmDeveloper(),
                        p.getProject().getClosingDate(),
                        p.getDateMatch())
                ).toList();

        return ResponseEntity.ok(dto);
    }


    @PostMapping
    @PreAuthorize("hasRole('USER')")
    @Operation(summary = "Cadastra um Match em um projeto")
    public ResponseEntity<Void> matchBusiness(@RequestBody @Valid ProjectBusinessDTO request) {
        Optional<Project> projOpt = projectService.getProjectById(UUID.fromString(request.getIdProject()));
        if (projOpt.isEmpty()){
            return ResponseEntity.notFound().build();
        }

        ProjectBusiness business = new ProjectBusiness();
        business.setProject(projOpt.get());
        business.setUserDeveloper(logged.load());
        business.setConfirmDeveloper(request.isConfirm());
        service.save(business);

        return ResponseEntity.status(HttpStatus.CREATED.value()).build();
    }


    @PutMapping("{id}")
    @PreAuthorize("hasRole('USER')")
    @Operation(summary = "Altera o próprio match")
    public ResponseEntity<Void> updateMatch(
            @PathVariable String id,
            @RequestBody @Valid ProjectBusinessConfirmationDTO request
    ) {
        Optional<ProjectBusiness> projectOpt = service.getByIdProjectBusiness(UUID.fromString(id));
        if (projectOpt.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        ProjectBusiness proj = projectOpt.get();
        proj.setConfirmDeveloper(request.isConfirmation());
        service.save(proj);
        return ResponseEntity.ok().build();
    }


    @DeleteMapping("{id}")
    @PreAuthorize("hasRole('USER')")
    @Operation(summary = "Usuário deleta o próprio projeto")
    public ResponseEntity<Void> deleteProjectByUser(@PathVariable String id) {
        Optional<ProjectBusiness> projectOptional = service.getByIdProjectBusiness(UUID.fromString(id));
        if (projectOptional.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        service.delete(projectOptional.get());
        return ResponseEntity.noContent().build();
    }

}