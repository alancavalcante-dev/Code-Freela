package io.github.alancavalcante_dev.araraflyapi.domain.portfolio.controller;

import io.github.alancavalcante_dev.araraflyapi.domain.portfolio.entity.PortfolioDeveloper;
import io.github.alancavalcante_dev.araraflyapi.domain.portfolio.service.PortfolioDeveloperService;
import io.github.alancavalcante_dev.araraflyapi.infrastructure.security.UserLogged;
import io.github.alancavalcante_dev.araraflyapi.domain.portfolio.dto.PortfolioDeveloperDTO;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/developer/portfolio/me")
@Tag(name="Portfólio do próprio desenvolvedor")
public class PortfolioDeveloperMeController {

    private final PortfolioDeveloperService service;
    private final UserLogged logged;

    @PreAuthorize("hasRole('DEVELOPER')")
    @GetMapping
    public ResponseEntity<PortfolioDeveloperDTO> getPortfolioDeveloperByUserLogged() {
        Optional<PortfolioDeveloper> developer = service.getPortfolioDeveloperByIdDeveloper(logged.load().getId());
        if (developer.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        PortfolioDeveloper portfolio = developer.get();

        PortfolioDeveloperDTO dto = new PortfolioDeveloperDTO();
        dto.setName(portfolio.getProfile().getName());
        dto.setName(portfolio.getProfile().getEmail());
        dto.setPresentation(portfolio.getPresentation());
        dto.setResume(portfolio.getResume());
        dto.setEnvironments(portfolio.getEnvironments());

        return ResponseEntity.ok(dto);
    }

    @PreAuthorize("hasRole('DEVELOPER')")
    @PutMapping
    public ResponseEntity<PortfolioDeveloperDTO> updatePortfolioDeveloperByUserLogged(
            @RequestBody @Valid PortfolioDeveloperDTO data) {
        Optional<PortfolioDeveloper> developer = service.getPortfolioDeveloperByIdDeveloper(logged.load().getId());
        if (developer.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        PortfolioDeveloperDTO dto = new PortfolioDeveloperDTO();
        dto.setName(data.getName());
        dto.setName(data.getEmail());
        dto.setPresentation(data.getPresentation());
        dto.setResume(data.getResume());
        dto.setEnvironments(data.getEnvironments());

        return ResponseEntity.ok(dto);
    }


}
