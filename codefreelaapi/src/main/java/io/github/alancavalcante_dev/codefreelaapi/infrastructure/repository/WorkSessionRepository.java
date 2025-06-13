package io.github.alancavalcante_dev.codefreelaapi.infrastructure.repository;

import io.github.alancavalcante_dev.codefreelaapi.domain.entity.Container;
import io.github.alancavalcante_dev.codefreelaapi.domain.entity.WorkSession;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;


@Repository
public interface WorkSessionRepository extends JpaRepository<WorkSession, UUID> {
}