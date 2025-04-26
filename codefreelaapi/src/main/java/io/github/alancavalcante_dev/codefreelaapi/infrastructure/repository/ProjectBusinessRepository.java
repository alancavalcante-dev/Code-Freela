package io.github.alancavalcante_dev.codefreelaapi.infrastructure.repository;

import io.github.alancavalcante_dev.codefreelaapi.domain.entity.ProjectBusiness;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface ProjectBusinessRepository extends JpaRepository<ProjectBusiness, UUID> {

    @Query("select p from ProjectBusiness p where p.userDeveloper.id = :idUser")
    List<ProjectBusiness> getAllByIdUser(UUID idUser);

    @Query("select p from ProjectBusiness p where p.userDeveloper.id = :idUser and confirmDeveloper = true")
    List<ProjectBusiness> getAllByIdUserIsConfirm(UUID idUser);
}
