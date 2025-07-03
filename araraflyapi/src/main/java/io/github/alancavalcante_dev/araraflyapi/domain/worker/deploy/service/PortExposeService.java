package io.github.alancavalcante_dev.araraflyapi.domain.worker.deploy.service;

import io.github.alancavalcante_dev.araraflyapi.domain.container.entity.Container;
import io.github.alancavalcante_dev.araraflyapi.domain.worker.deploy.entity.PortExpose;
import io.github.alancavalcante_dev.araraflyapi.infrastructure.repository.PortExposeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;



@Service
public class PortExposeService {

    @Autowired
    private PortExposeRepository portExposeRepository;


    public List<PortExpose> getBySurnameServiceAndContainer(Container container, String nameDeploy) {
        return portExposeRepository.getBySurnameServiceAndIdContainer(nameDeploy, container.getIdContainer());
    }

    public PortExpose save(PortExpose portExpose) {
        return portExposeRepository.save(portExpose);
    }

    public void delete(PortExpose portExpose) {
        portExposeRepository.delete(portExpose);
    }
}