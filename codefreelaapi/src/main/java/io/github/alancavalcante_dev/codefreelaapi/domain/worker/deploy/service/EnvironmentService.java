package io.github.alancavalcante_dev.codefreelaapi.domain.worker.deploy.service;

import io.github.alancavalcante_dev.codefreelaapi.domain.container.entity.Container;
import io.github.alancavalcante_dev.codefreelaapi.domain.worker.deploy.entity.Environment;
import io.github.alancavalcante_dev.codefreelaapi.infrastructure.repository.EnvironmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EnvironmentService {

    @Autowired
    private EnvironmentRepository environmentRepository;


    public List<Environment> getBySurnameServiceAndContainer(Container container, String nameDeploy) {
        return environmentRepository.getBySurnameServiceAndIdContainer(nameDeploy, container.getIdContainer());
    }

    public Environment save(Environment environment) {
        return environmentRepository.save(environment);
    }

    public void delete(Environment environment) {
        environmentRepository.delete(environment);
    }
}
