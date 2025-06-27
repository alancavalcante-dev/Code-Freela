package io.github.alancavalcante_dev.codefreelaapi.domain.worker.deploy.service;

import io.github.alancavalcante_dev.codefreelaapi.domain.container.entity.Container;
import io.github.alancavalcante_dev.codefreelaapi.domain.worker.deploy.entity.Deploy;
import io.github.alancavalcante_dev.codefreelaapi.infrastructure.repository.DeployRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@Service
public class DeployService {

    @Autowired
    private DeployRepository deployRepository;

    public Optional<Deploy> getDeploy(Container container) {
        return deployRepository.findByContainer(container);
    }

    public Deploy save(Deploy deploy) {
        return deployRepository.save(deploy);
    }

}
