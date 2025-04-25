package io.github.alancavalcante_dev.codefreelaapi.presentation.controller;

import io.github.alancavalcante_dev.codefreelaapi.domain.entity.Profile;
import io.github.alancavalcante_dev.codefreelaapi.domain.entity.Project;
import io.github.alancavalcante_dev.codefreelaapi.domain.project.ProjectService;
import io.github.alancavalcante_dev.codefreelaapi.mapper.ProjectMapper;
import io.github.alancavalcante_dev.codefreelaapi.presentation.dto.profile.ProfileResponseDTO;
import io.github.alancavalcante_dev.codefreelaapi.presentation.dto.project.ProjectDTO;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("api/projects")
@RequiredArgsConstructor
@Tag(name = "Projetos")
public class ProjectController {

    private final ProjectService service;
    private final ProjectMapper mapper;

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<ProjectDTO>> getAllProjects() {
        List<Project> allProjects = service.getAllProjects();
        List<ProjectDTO> listProjectDTO = allProjects.stream().map(mapper::toDTO).toList();

        if (listProjectDTO.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(listProjectDTO);
    }

    @PostMapping
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<Void> registerProject(@RequestBody @Valid ProjectDTO dto) {
        Project project = service.register(mapper.toEntity(dto));

        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(project.getIdProject()).toUri();

        return ResponseEntity.created(uri).build();
    }
}










