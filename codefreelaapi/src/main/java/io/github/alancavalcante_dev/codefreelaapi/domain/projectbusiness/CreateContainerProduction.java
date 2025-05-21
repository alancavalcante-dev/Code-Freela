package io.github.alancavalcante_dev.codefreelaapi.domain.projectbusiness;

import io.github.alancavalcante_dev.codefreelaapi.domain.entity.Container;
import io.github.alancavalcante_dev.codefreelaapi.domain.entity.Profile;
import io.github.alancavalcante_dev.codefreelaapi.domain.entity.ProjectBusiness;
import io.github.alancavalcante_dev.codefreelaapi.domain.entity.enums.StateProject;
import io.github.alancavalcante_dev.codefreelaapi.domain.notification.NotificationEmailSender;
import io.github.alancavalcante_dev.codefreelaapi.infrastructure.repository.ContainerRepository;
import io.github.alancavalcante_dev.codefreelaapi.infrastructure.repository.ProfileRepository;
import jakarta.transaction.Transactional;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Getter
@Setter
@Service
@RequiredArgsConstructor
public class CreateContainerProduction {

    private final ContainerRepository containerRepository;
    private final ProfileRepository profileRepository;
    private final NotificationEmailSender notification;
    private final ProjectBusiness project;

    @Transactional
    public void create() {
        Container container = new Container();

        container.setProjectBusiness(project);
        container.setStateProject(StateProject.DIDNTSTART);
        containerRepository.save(container);
        log.info("Container do projeto criado");

        Profile client = profileRepository.getByIdUser(project.getProject().getUser().getId())
                .orElseThrow( () -> new RuntimeException("Usuário inexistente") );

        Profile developer = profileRepository.getByIdUser(project.getUserDeveloper().getId())
                .orElseThrow( () -> new RuntimeException("Usuário inexistente") );

        String body = bodyEmailCreated(client, developer);
        String subject = "MATCH - Projeto iniciado com sucesso";

        notification.send(client.getEmail(), subject, body);
        notification.send(developer.getEmail(), subject, body);
    }

    public String bodyEmailCreated(Profile Client, Profile developer) {
        String bodyClient = "Olá,<br>Projeto iniciado pelo colaboradores: <br>Cliente:%s".formatted(Client.getName());
        String bodyDeveloper = "<br>Desenvolvedor: %s".formatted(developer.getName());
        return bodyClient + bodyDeveloper;
    }

}
