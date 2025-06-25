package io.github.alancavalcante_dev.codefreelaapi.domain.portfolio.controller;

import io.github.alancavalcante_dev.codefreelaapi.domain.portfolio.entity.PortfolioDeveloper;
import io.github.alancavalcante_dev.codefreelaapi.domain.portfolio.service.PortfolioDeveloperService;
import io.github.alancavalcante_dev.codefreelaapi.infrastructure.security.UserLogged;
import io.github.alancavalcante_dev.codefreelaapi.domain.portfolio.dto.PortfolioDeveloperDTO;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/developer/portfolio/me")
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
        dto.setLanguages(portfolio.getLanguages());

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
        dto.setLanguages(data.getLanguages());

        return ResponseEntity.ok(dto);
    }


}
