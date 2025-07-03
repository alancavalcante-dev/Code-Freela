package io.github.alancavalcante_dev.codefreelaapi.infrastructure.repository;

import io.github.alancavalcante_dev.codefreelaapi.domain.container.entity.Container;
import io.github.alancavalcante_dev.codefreelaapi.domain.worker.deploy.entity.Deploy;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface DeployRepository extends JpaRepository<Deploy, UUID> {

//    @Query("select d from Deploy d where d.container.idContainer = :idContainer")
    Optional<Deploy> getByContainer(Container container);

    List<Deploy> findBySurnameServiceAndContainer(String surnameService, Container container);


}
