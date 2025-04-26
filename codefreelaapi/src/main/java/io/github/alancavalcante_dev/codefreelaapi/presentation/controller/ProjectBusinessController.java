package io.github.alancavalcante_dev.codefreelaapi.presentation.controller;

import io.github.alancavalcante_dev.codefreelaapi.domain.entity.Project;
import io.github.alancavalcante_dev.codefreelaapi.domain.entity.ProjectBusiness;
import io.github.alancavalcante_dev.codefreelaapi.domain.project.ProjectService;
import io.github.alancavalcante_dev.codefreelaapi.domain.projectbusiness.ProjectBusinessService;
import io.github.alancavalcante_dev.codefreelaapi.infrastructure.security.UserLogged;
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
import java.util.stream.Collectors;

@RestController
@RequestMapping("api/user/projects/business")
@RequiredArgsConstructor
@Tag(name = "Negociação de projeots - Match de developer")
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

}
