package io.github.alancavalcante_dev.araraflyapi.domain.portfolio.controller;

import io.github.alancavalcante_dev.araraflyapi.domain.portfolio.entity.PortfolioDeveloper;
import io.github.alancavalcante_dev.araraflyapi.domain.portfolio.service.PortfolioDeveloperService;
import io.github.alancavalcante_dev.araraflyapi.domain.portfolio.dto.PortfolioDeveloperDTO;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;


@RestController
@RequiredArgsConstructor
@RequestMapping("api/portfolio/developer")
@Tag(name="Consulta de desenvolvedores")
public class PortfolioDeveloperController {


    private final PortfolioDeveloperService service;


    @GetMapping("{id}")
    public ResponseEntity<PortfolioDeveloperDTO> getByIdDeveloperPortfolio(@PathVariable String id) {
        Optional<PortfolioDeveloper> devOpt = service.getPortfolioDeveloperByIdDeveloper(UUID.fromString(id));
        if (devOpt.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        PortfolioDeveloper port = devOpt.get();

        PortfolioDeveloperDTO dto = new PortfolioDeveloperDTO();
        dto.setIdDeveloper(port.getIdDeveloper().toString());
        dto.setName(port.getProfile().getName());
        dto.setEmail(port.getProfile().getEmail());
        dto.setPresentation(port.getPresentation());
        dto.setResume(port.getResume());
        dto.setEnvironments(port.getEnvironments());

        return ResponseEntity.ok(dto);
    }


    @GetMapping
    public ResponseEntity<List<PortfolioDeveloperDTO>> getAllDevelopers(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String email,
            @RequestParam(required = false) String language
    ) {
        List<PortfolioDeveloper> filteredDevelopers = service.getFilteredDevelopers(name, email, language);

        List<PortfolioDeveloperDTO> developers = filteredDevelopers.stream()
                .map(d -> {
                    PortfolioDeveloperDTO dto = new PortfolioDeveloperDTO();
                    dto.setIdDeveloper(d.getIdDeveloper().toString());
                    dto.setName(d.getProfile().getName());
                    dto.setEmail(d.getProfile().getEmail());
                    dto.setPresentation(d.getPresentation());
                    dto.setResume(d.getResume());
                    dto.setEnvironments(d.getEnvironments());
                    return dto;
                }).toList();

        if (developers.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(developers);
    }



}


