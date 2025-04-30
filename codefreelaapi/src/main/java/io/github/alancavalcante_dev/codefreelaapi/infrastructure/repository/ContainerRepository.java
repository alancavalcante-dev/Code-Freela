package io.github.alancavalcante_dev.codefreelaapi.infrastructure.repository;

import io.github.alancavalcante_dev.codefreelaapi.domain.entity.Container;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface ContainerRepository extends JpaRepository<Container, UUID> {

    @Query("select c from Container c where c.projectBusiness.userDeveloper.user.id = :idUser")
    List<Container> getContainersByIdUser(UUID idUser);

}
