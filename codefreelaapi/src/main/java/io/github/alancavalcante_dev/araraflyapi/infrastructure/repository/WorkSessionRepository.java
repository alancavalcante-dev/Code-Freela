package io.github.alancavalcante_dev.araraflyapi.infrastructure.repository;

import io.github.alancavalcante_dev.araraflyapi.domain.appointment.entity.Appointment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;


@Repository
public interface WorkSessionRepository extends JpaRepository<Appointment, UUID> {
}