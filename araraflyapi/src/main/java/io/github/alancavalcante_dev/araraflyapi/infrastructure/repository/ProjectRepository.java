package io.github.alancavalcante_dev.araraflyapi.infrastructure.repository;


import io.github.alancavalcante_dev.araraflyapi.domain.project.entity.Project;
import io.github.alancavalcante_dev.araraflyapi.domain.entity.enums.StateBusiness;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;


@Repository
public interface ProjectRepository extends JpaRepository<Project, UUID>, JpaSpecificationExecutor<Project> {

    @Query("Select p From Project p Where p.user.id = :idUser and p.idProject = :IdProject")
    Optional<Project> getProjectByIdProjectByUserId(UUID idUser, UUID IdProject);

    @Query("select p from Project p where p.user.id = :idUserProject and p.stateBusiness = 'WORKING' and p.idProject = :idProject")
    Optional<Project> getStateBusinessWorking(UUID idUserProject, UUID idProject);

    @Query("select p from Project p where p.user.id = :idUser and p.stateBusiness = :state")
    List<Project> getProjectsByUserForStateBusiness(UUID idUser, StateBusiness state);






}
