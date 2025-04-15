package io.github.alancavalcante_dev.codefreelaapi.repository;


import io.github.alancavalcante_dev.codefreelaapi.model.BusinessProjectProfile;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface BusinessProjectProfileRepository extends JpaRepository<BusinessProjectProfile, UUID> {
}
