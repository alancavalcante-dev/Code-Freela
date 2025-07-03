package io.github.alancavalcante_dev.araraflyapi.domain.worker.deploy.service;

import io.github.alancavalcante_dev.araraflyapi.domain.container.entity.Container;
import io.github.alancavalcante_dev.araraflyapi.domain.worker.deploy.entity.Deploy;
import io.github.alancavalcante_dev.araraflyapi.infrastructure.repository.DeployRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Slf4j
@Service
public class DeployService {

    @Autowired
    private DeployRepository deployRepository;

    public Optional<Deploy> getDeploy(Container container) {
        return deployRepository.getByContainer(container);
    }

    public Optional<Deploy> getDeployById(UUID id) {
        return deployRepository.findById(id);
    }


    public Deploy save(Deploy deploy) {
        return deployRepository.save(deploy);
    }

    public List<Deploy> findBySurnameServiceAndContainer(String surnameService, Container container) {
        return deployRepository.findBySurnameServiceAndContainer(surnameService, container);
    }




}
