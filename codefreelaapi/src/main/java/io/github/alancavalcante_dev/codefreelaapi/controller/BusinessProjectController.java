package io.github.alancavalcante_dev.codefreelaapi.controller;


import io.github.alancavalcante_dev.codefreelaapi.dto.businesproject.BusinessProjectInsertDTO;
import io.github.alancavalcante_dev.codefreelaapi.dto.businesproject.BusinessProjectResponseDTO;
import io.github.alancavalcante_dev.codefreelaapi.dto.businesproject.BusinessProjectUpdateDTO;
import io.github.alancavalcante_dev.codefreelaapi.mapperstruct.BusinessProjectMapper;
import io.github.alancavalcante_dev.codefreelaapi.model.BusinessProject;
import io.github.alancavalcante_dev.codefreelaapi.service.BusinessProjectService;
import io.github.alancavalcante_dev.codefreelaapi.specifications.BusinessProjectSpecification;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.math.BigDecimal;
import java.net.URI;
import java.time.LocalDate;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("api/projects/business")
@Tag(name = "Negociação de projeto")
public class BusinessProjectController {

    @Autowired
    private BusinessProjectService service;

    @Autowired
    private BusinessProjectMapper mapper;


    @GetMapping
    @Operation(summary = "Pega todas negociações de projetos")
    public ResponseEntity<Page<BusinessProjectResponseDTO>> getAllBusinessProject(
            @RequestParam(value = "title", required = false) String title,
            @RequestParam(value = "description", required = false) String description,
            @RequestParam(value = "price-day", required = false) BigDecimal priceDay,
            @RequestParam(value = "price-hour", required = false) BigDecimal priceHour,
            @RequestParam(value = "price-project", required = false) BigDecimal priceProject,
            @RequestParam(value = "closing-date", required = false) LocalDate closingDate,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
            ) {

        Specification<BusinessProject> spec = Specification.where(null);
        spec.and(BusinessProjectSpecification.hasTitle(title));
        spec.and(BusinessProjectSpecification.hasDescription(description));
        spec.and(BusinessProjectSpecification.gtaOrEqualPriceDay(priceDay));
        spec.and(BusinessProjectSpecification.gtaOrEqualPriceHour(priceHour));
        spec.and(BusinessProjectSpecification.gtaOrEqualPriceProject(priceProject));
        spec.and(BusinessProjectSpecification.gtaOrEqualClosingDate(closingDate));

        Pageable pageable = PageRequest.of(page, size);
        Page<BusinessProject> result = service.findAllWithPage(spec, pageable);
        Page<BusinessProjectResponseDTO> resultDTO = result.map(project -> mapper.entityToResponse(project));

        if(result.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(resultDTO);
    }


    @GetMapping("{id}")
    @Operation(summary = "Pega a negociação de projeto por Id")
    public ResponseEntity<BusinessProjectResponseDTO> getIdBusinessProject(@PathVariable("id") String id) {
        Optional<BusinessProject> projectOptional = service.findyByIdBusinessProject(UUID.fromString(id));
        if (projectOptional.isEmpty()) { return ResponseEntity.notFound().build(); }

        BusinessProject project = projectOptional.get();
        BusinessProjectResponseDTO dto = mapper.entityToResponse(project);

        return ResponseEntity.ok(dto);
    }


    @PostMapping
    @Operation(summary = "Cadastra uma negociação de projeto")
    public ResponseEntity<BusinessProjectInsertDTO> postBusinessProject(@RequestBody @Valid BusinessProjectInsertDTO request) throws Exception {
        BusinessProject business = service.save(request);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().
                path("/{id}").buildAndExpand(business.getIdBusinessProject()).toUri();

        return ResponseEntity.created(location).build();
    }


    @PutMapping("{id}")
    @Operation(summary = "Altera a negociação de projeto por Id")
    public ResponseEntity<Object> updateBusinessProject(
            @PathVariable("id") String id,
            @RequestBody @Valid BusinessProjectUpdateDTO request
    ) {
        Optional<BusinessProject> businessOptional = service.findyByIdBusinessProject(UUID.fromString(id));
        if (businessOptional.isEmpty()) { return ResponseEntity.notFound().build(); }

        BusinessProject project = businessOptional.get();
        project.setTitle(request.getTitle());
        project.setDescription(request.getDescription());
        project.setPriceDay(request.getPriceDay());
        project.setPriceHour(request.getPriceHour());
        project.setPriceProject(request.getPriceProject());

        service.update(project);
        return ResponseEntity.ok().build();
    }


    @DeleteMapping("{id}")
    @Operation(summary = "Deleta uma negociação de projeto")
    public ResponseEntity<Object> deleteBusinessProject(@PathVariable("id") String idBusinessProject) {
        return service.findyByIdBusinessProject(UUID.fromString(idBusinessProject))
                .map( project -> {
                    service.delete(project);
                    return ResponseEntity.noContent().build();
                }).orElseGet( () -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }
}
