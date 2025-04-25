package io.github.alancavalcante_dev.codefreelaapi.presentation.controller;

import io.github.alancavalcante_dev.codefreelaapi.domain.entity.Project;
import io.github.alancavalcante_dev.codefreelaapi.domain.entity.StateBusiness;
import io.github.alancavalcante_dev.codefreelaapi.domain.project.ProjectService;
import io.github.alancavalcante_dev.codefreelaapi.infrastructure.security.UserLogged;
import io.github.alancavalcante_dev.codefreelaapi.mapper.ProjectMapper;
import io.github.alancavalcante_dev.codefreelaapi.presentation.dto.project.ProjectDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.logging.Logger;

@RestController
@RequestMapping("api/user/projects")
@RequiredArgsConstructor
@Tag(name = "Projetos do usuário")
public class ProjectController {


    private final ProjectService service;
    private final ProjectMapper mapper;


    @GetMapping("{stateBusiness}")
    @PreAuthorize("hasRole('USER')")
    @Operation(summary = "Pega os projetos do próprio usuário com consulta personalizada")
    public ResponseEntity<List<ProjectDTO>> getProjectsOpenByUser(@PathVariable String stateBusiness) {
        List<Project> projects = service.getProjectsByUserForStateBusiness(UserLogged.load(), StateBusiness.valueOf(stateBusiness));
        if (projects.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        List<ProjectDTO> projectDtos = projects.stream().map(mapper::toDTO).toList();
        return ResponseEntity.ok(projectDtos);
    }


    @PostMapping
    @PreAuthorize("hasRole('USER')")
    @Operation(summary = "Usuário cadastra um projeto")
    public ResponseEntity<Void> registerProjectByUser(@RequestBody @Valid ProjectDTO dto) {
        Project project = mapper.toEntity(dto);
        project.setUser(UserLogged.load());
        Project projectSaved = service.save(project);

        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(projectSaved.getIdProject()).toUri();

        return ResponseEntity.created(uri).build();
    }


    @PutMapping("{id}")
    @PreAuthorize("hasRole('USER')")
    @Operation(summary = "Usuário altera o próprio projeto")
    public ResponseEntity<ProjectDTO> updateProjectByUser(@PathVariable String id, @RequestBody @Valid ProjectDTO dto) {
        Optional<Project> projectOptional = service.getProjectById(UUID.fromString(id));
        if (projectOptional.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        Project project = projectOptional.get();
        project.setTitle(dto.getTitle());
        project.setDescription(dto.getDescription());
        project.setPriceDay(dto.getPriceDay());
        project.setPriceHour(dto.getPriceHour());
        project.setPriceProject(dto.getPriceProject());
        project.setClosingDate(dto.getClosingDate());

        Project projectSaved = service.save(project);

        return ResponseEntity.ok(mapper.toDTO(projectSaved));
    }


    @DeleteMapping("{id}")
    @PreAuthorize("hasRole('USER')")
    @Operation(summary = "Usuário deleta o próprio projeto")
    public ResponseEntity deleteProjectByUser(@PathVariable String id) {
        Optional<Project> projectOptional = service.getProjectById(UUID.fromString(id));
        if (projectOptional.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        service.delete(projectOptional.get());
        return ResponseEntity.noContent().build();
    }

}










