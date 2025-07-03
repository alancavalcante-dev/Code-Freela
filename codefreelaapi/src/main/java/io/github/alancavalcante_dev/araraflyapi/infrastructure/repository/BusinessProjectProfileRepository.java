package io.github.alancavalcante_dev.araraflyapi.infrastructure.repository;


import io.github.alancavalcante_dev.araraflyapi.domain.match.entity.ProjectBusiness;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface BusinessProjectProfileRepository extends JpaRepository<ProjectBusiness, UUID> {
}
