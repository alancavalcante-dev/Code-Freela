package io.github.alancavalcante_dev.araraflyapi.infrastructure.repository;

import io.github.alancavalcante_dev.araraflyapi.domain.entity.Address;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface AddressRepository extends JpaRepository<Address, UUID> {
}
