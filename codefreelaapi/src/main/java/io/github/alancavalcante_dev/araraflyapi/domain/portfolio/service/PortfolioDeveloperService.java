package io.github.alancavalcante_dev.araraflyapi.domain.portfolio.service;

import io.github.alancavalcante_dev.araraflyapi.domain.portfolio.entity.PortfolioDeveloper;
import io.github.alancavalcante_dev.araraflyapi.domain.portfolio.controller.PortfolioDeveloperSpecification;
import io.github.alancavalcante_dev.araraflyapi.infrastructure.repository.PortfolioDeveloperRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PortfolioDeveloperService {

    private final PortfolioDeveloperRepository repository;

    public Optional<PortfolioDeveloper> getPortfolioDeveloperByIdDeveloper(UUID uuid) {
        return repository.getPortfolioDeveloperByIdUser(uuid);
    }

    public List<PortfolioDeveloper> getAllDevelopers() {
        return repository.findAll();
    }

    public List<PortfolioDeveloper> getFilteredDevelopers(String name, String email, String language) {
        Specification<PortfolioDeveloper> spec = Specification
                .where(PortfolioDeveloperSpecification.nameContains(name))
                .and(PortfolioDeveloperSpecification.emailEquals(email))
                .and(PortfolioDeveloperSpecification.hasLanguage(language));

        return repository.findAll(spec);
    }


}
