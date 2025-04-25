package io.github.alancavalcante_dev.codefreelaapi.infrastructure.repository;


import io.github.alancavalcante_dev.codefreelaapi.domain.entity.ProjectBusiness;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface BusinessProjectProfileRepository extends JpaRepository<ProjectBusiness, UUID> {
}
