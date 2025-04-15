package io.github.alancavalcante_dev.codefreelaapi.repository;

import io.github.alancavalcante_dev.codefreelaapi.model.BusinessProject;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.UUID;

public interface BusinessProjectRepository extends
        JpaRepository<BusinessProject, UUID>,
        JpaSpecificationExecutor<BusinessProject> {

}
