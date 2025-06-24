package io.github.alancavalcante_dev.codefreelaapi.infrastructure.repository;

import io.github.alancavalcante_dev.codefreelaapi.domain.match.entity.ProjectBusiness;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface ProjectBusinessRepository extends JpaRepository<ProjectBusiness, UUID> {

    @Query("select p from ProjectBusiness p where p.userDeveloper.id = :idUser")
    List<ProjectBusiness> getAllByIdUserDeveloper(UUID idUser);

    @Query("select p from ProjectBusiness p where p.project.user.id = :idUser")
    List<ProjectBusiness> getAllByIdUserClient(UUID idUser);

    @Query("select p from ProjectBusiness p where p.userDeveloper.id = :idUser and confirmDeveloper = true")
    List<ProjectBusiness> getAllByIdUserIsConfirm(UUID idUser);

    @Query("Select p From ProjectBusiness p Where p.userDeveloper.id = :idUser And p.idProjectBusiness = :idProjectBusiness")
    Optional<ProjectBusiness> getByIdProjectBusinessForUserDeveloper(UUID idUser, UUID idProjectBusiness);

    @Query("Select p From ProjectBusiness p Where p.project.user.id = :idUser And p.idProjectBusiness = :idProjectBusiness")
    Optional<ProjectBusiness> getByIdProjectBusinessForUserClient(UUID idUser, UUID idProjectBusiness);
}
