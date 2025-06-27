package io.github.alancavalcante_dev.codefreelaapi.infrastructure.repository;


import io.github.alancavalcante_dev.codefreelaapi.domain.worker.deploy.entity.Environment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;



@Repository
public interface EnvironmentRepository extends JpaRepository<Environment, UUID> {

    @Query("select e from Environment e where e.deploy.surnameService = :nameDeploy and e.deploy.container.idContainer = :idContainer")
    List<Environment> getBySurnameServiceAndIdContainer(String nameDeploy, UUID idContainer);

}
