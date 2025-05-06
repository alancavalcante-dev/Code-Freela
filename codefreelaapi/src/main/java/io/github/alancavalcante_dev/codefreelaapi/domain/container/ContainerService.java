package io.github.alancavalcante_dev.codefreelaapi.domain.container;


import io.github.alancavalcante_dev.codefreelaapi.domain.entity.Container;
import io.github.alancavalcante_dev.codefreelaapi.domain.entity.User;
import io.github.alancavalcante_dev.codefreelaapi.infrastructure.repository.ContainerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;


@Service
@RequiredArgsConstructor
public class ContainerService {


    private final ContainerRepository repository;


    public List<Container> getContainersByUser(User user) {
        return repository.getContainersByIdUser(user.getId());
    }

    public Optional<Container> getContainerById(UUID idUser, UUID idContainer) {
        return repository.getContainerById(idUser, idContainer);
    }

}
