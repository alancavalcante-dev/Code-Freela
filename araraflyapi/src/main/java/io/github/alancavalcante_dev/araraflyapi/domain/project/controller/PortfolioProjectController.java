package io.github.alancavalcante_dev.araraflyapi.domain.project.controller;

import io.github.alancavalcante_dev.araraflyapi.domain.project.entity.Project;
import io.github.alancavalcante_dev.araraflyapi.domain.project.service.ProjectService;
import io.github.alancavalcante_dev.araraflyapi.mapper.ProjectMapper;
import io.github.alancavalcante_dev.araraflyapi.domain.project.dto.ProjectDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/portfolio/projects")
@Tag(name = "Portfólio de Projetos")
public class PortfolioProjectController {


    private final ProjectMapper mapper;
    private final ProjectService service;


    @GetMapping("{id}")
    @Operation(summary = "Pega projeto especifico para ver detalhes")
    public ResponseEntity<ProjectDTO> getByIdProjectChosen(@PathVariable String id) {
        return service.getProjectById(UUID.fromString(id))
                .map(p -> ResponseEntity.ok(mapper.toDTO(p)))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }


    @GetMapping
    @Operation(summary = "Pega todos projetos estão abertos para desenvolver")
    public ResponseEntity<Page<ProjectDTO>> getAllProjectsOpenWithQueries(
            @RequestParam(value = "title", required = false) String title,
            @RequestParam(value = "description", required = false) String description,
            @RequestParam(value = "price-day", required = false) BigDecimal priceDay,
            @RequestParam(value = "price-hour", required = false) BigDecimal priceHour,
            @RequestParam(value = "price-project", required = false) BigDecimal priceProject,
            @RequestParam(value = "closing-date", required = false) LocalDate closingDate,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(value = "sortField", required = false) String sortField,
            @RequestParam(value = "sortDirection", defaultValue = "asc") String sortDirection
    )
    {
        Specification<Project> spec = ProjectSpecification.stateBusinessOpen();

        if (title != null && !title.isBlank()) {
            spec = spec.and(ProjectSpecification.hasTitle(title));
        }
        if (description != null && !description.isBlank()) {
            spec = spec.and(ProjectSpecification.hasDescription(description));
        }
        if (priceDay != null) {
            spec = spec.and(ProjectSpecification.gtaOrEqualPriceDay(priceDay));
        }
        if (priceHour != null) {
            spec = spec.and(ProjectSpecification.gtaOrEqualPriceHour(priceHour));
        }
        if (priceProject != null) {
            spec = spec.and(ProjectSpecification.gtaOrEqualPriceProject(priceProject));
        }
        if (closingDate != null) {
            spec = spec.and(ProjectSpecification.gtaOrEqualClosingDate(closingDate));
        }

        Pageable pageable;
        if (sortField != null && !sortField.isBlank()) {
            Sort.Direction direction = sortDirection.equalsIgnoreCase("desc") ? Sort.Direction.DESC : Sort.Direction.ASC;
            pageable = PageRequest.of(page, size, Sort.by(direction, sortField));
        } else {
            pageable = PageRequest.of(page, size);
        }

        Page<Project> result = service.findAllWithPage(spec, pageable);
        Page<ProjectDTO> resultDTO = result.map(mapper::toDTO);

        if (result.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(resultDTO);
    }


}
