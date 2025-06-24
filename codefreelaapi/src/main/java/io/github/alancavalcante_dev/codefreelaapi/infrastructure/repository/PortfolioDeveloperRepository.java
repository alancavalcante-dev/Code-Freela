package io.github.alancavalcante_dev.codefreelaapi.infrastructure.repository;

import io.github.alancavalcante_dev.codefreelaapi.domain.portfolio.entity.PortfolioDeveloper;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;


@Repository
public interface PortfolioDeveloperRepository extends JpaRepository<PortfolioDeveloper, UUID>,
        JpaSpecificationExecutor<PortfolioDeveloper> {

    @Query("Select p From PortfolioDeveloper p Where p.user.id = :IdUser")
    Optional<PortfolioDeveloper> getPortfolioDeveloperByIdUser(UUID IdUser);
}