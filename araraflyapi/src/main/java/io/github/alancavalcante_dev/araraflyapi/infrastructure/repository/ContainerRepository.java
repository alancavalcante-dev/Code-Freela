package io.github.alancavalcante_dev.araraflyapi.infrastructure.repository;

import io.github.alancavalcante_dev.araraflyapi.domain.container.entity.Container;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface ContainerRepository extends JpaRepository<Container, UUID> {

    @Query("select c from Container c where c.projectBusiness.userDeveloper.id = :idUser")
    List<Container> getContainersByIdUserDeveloper(UUID idUser);

    @Query("select c from Container c where c.projectBusiness.project.user.id = :user")
    List<Container> getContainersByUserClient(UUID user);

    @Query("Select c From Container c Where c.projectBusiness.userDeveloper.id = :idUser and idContainer = :idContainer")
    Optional<Container> getContainerById(UUID idUser, UUID idContainer);

}