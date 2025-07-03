package io.github.alancavalcante_dev.araraflyapi.domain.container.service;


import io.github.alancavalcante_dev.araraflyapi.domain.container.entity.Container;
import io.github.alancavalcante_dev.araraflyapi.domain.entity.User;
import io.github.alancavalcante_dev.araraflyapi.infrastructure.repository.ContainerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;


@Service
@RequiredArgsConstructor
public class ContainerService {


    private final ContainerRepository repository;

    public void save(Container container) {
        repository.save(container);
    }


    public List<Container> getContainersByUserDeveloper(User user) {
        return repository.getContainersByIdUserDeveloper(user.getId());
    }

    public List<Container> getContainersByUserClient(User user) {
        return repository.getContainersByUserClient(user.getId());
    }

    public Optional<Container> getContainerById(UUID idUser, UUID idContainer) {
        return repository.getContainerById(idUser, idContainer);
    }


}
