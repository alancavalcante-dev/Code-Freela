package io.github.alancavalcante_dev.codefreelaapi.infrastructure.repository;


import io.github.alancavalcante_dev.codefreelaapi.domain.entity.Project;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;


@Repository
public interface ProjectRepository extends JpaRepository<Project, UUID> {

    @Query("select p from Project p where p.user.id = :idUser and p.stateBusiness = 'OPEN'")
    List<Project> getProjectsOpenByUser(UUID idUser);

}
