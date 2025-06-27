package io.github.alancavalcante_dev.codefreelaapi.infrastructure.repository;


import io.github.alancavalcante_dev.codefreelaapi.domain.container.entity.Container;
import io.github.alancavalcante_dev.codefreelaapi.domain.worker.deploy.entity.PortExpose;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface PortExposeRepository extends JpaRepository<PortExpose, UUID> {

    @Query("select p from PortExpose p where p.deploy.surnameService = :nameDeploy and p.deploy.container.idContainer = :idContainer")
    List<PortExpose> getBySurnameServiceAndIdContainer(String nameDeploy, UUID idContainer);

}
