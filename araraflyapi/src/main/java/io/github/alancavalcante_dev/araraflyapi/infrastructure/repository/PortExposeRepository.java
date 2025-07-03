package io.github.alancavalcante_dev.araraflyapi.infrastructure.repository;


import io.github.alancavalcante_dev.araraflyapi.domain.worker.deploy.entity.PortExpose;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface PortExposeRepository extends JpaRepository<PortExpose, UUID> {

    @Query("SELECT p FROM PortExpose p " +
            "JOIN FETCH p.deploy d " +        // Traz o Deploy junto com a PortExpose
            "JOIN d.container c " +           // Faz o join normal para a cláusula WHERE
            "WHERE d.surnameService = :nameDeploy AND c.idContainer = :idContainer")
    List<PortExpose> getBySurnameServiceAndIdContainer(String nameDeploy, UUID idContainer);

}
