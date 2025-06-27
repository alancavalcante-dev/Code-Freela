package io.github.alancavalcante_dev.codefreelaapi.infrastructure.repository;

import io.github.alancavalcante_dev.codefreelaapi.domain.worker.deploy.entity.Deploy;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface DeployRepository extends JpaRepository<Deploy, UUID> {

    @Query("select d from Deploy d where d.container.projectBusiness.userDeveloper = :idUser and d.container.idContainer = :idContainer")
    Optional<Deploy> getDeployByContainer(String idUser, String idContainer);

}
