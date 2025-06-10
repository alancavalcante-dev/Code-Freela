package io.github.alancavalcante_dev.codefreelaapi.domain.projectbusiness;

import io.github.alancavalcante_dev.codefreelaapi.domain.entity.Container;
import io.github.alancavalcante_dev.codefreelaapi.domain.entity.Profile;
import io.github.alancavalcante_dev.codefreelaapi.domain.entity.ProjectBusiness;
import io.github.alancavalcante_dev.codefreelaapi.domain.entity.enums.StateBusiness;
import io.github.alancavalcante_dev.codefreelaapi.domain.entity.enums.StateProject;
import io.github.alancavalcante_dev.codefreelaapi.domain.notification.NotificationEmailSender;
import io.github.alancavalcante_dev.codefreelaapi.infrastructure.repository.ContainerRepository;
import io.github.alancavalcante_dev.codefreelaapi.infrastructure.repository.ProfileRepository;
import io.github.alancavalcante_dev.codefreelaapi.infrastructure.repository.ProjectBusinessRepository;
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
public class CreateContainerProduction {

    private ContainerRepository containerRepository;
    private ProfileRepository profileRepository;
    private NotificationEmailSender notification;
    private ProjectBusinessRepository projectBusinessRepository;
    private ProjectBusiness project;


    public CreateContainerProduction(
            ContainerRepository containerRepository,
            ProfileRepository profileRepository,
            NotificationEmailSender notification,
            ProjectBusinessRepository projectBusinessRepository,
            ProjectBusiness project
    ) {
        this.containerRepository = containerRepository;
        this.profileRepository = profileRepository;
        this.notification = notification;
        this.projectBusinessRepository = projectBusinessRepository;
        this.project = project;
    }


    @Transactional
    public void create() {
        Container container = new Container();

        Profile client = profileRepository.getByIdUser(project.getProject().getUser().getId())
                .orElseThrow( () -> new RuntimeException("Perfil do usuário inexistente") );

        Profile developer = profileRepository.getByIdUser(project.getUserDeveloper().getId())
                .orElseThrow( () -> new RuntimeException("Perfil do usuário inexistente") );

        project.getProject().setStateBusiness(StateBusiness.WORKING);
        projectBusinessRepository.save(project);

        container.setProjectBusiness(project);
        container.setStateProject(StateProject.DIDNTSTART);
        containerRepository.save(container);
        log.info("Container do projeto criado");

        String body = bodyEmailCreated(client, developer);
        String subject = "MATCH - Projeto iniciado com sucesso";

        notification.send(developer.getEmail(), subject, body);
        log.info("Notificações enviadas");


    }

    public String bodyEmailCreated(Profile Client, Profile developer) {
        String bodyClient = "Olá,<br>Projeto iniciado pelo colaboradores: <br>Cliente:%s".formatted(Client.getName());
        String bodyDeveloper = "<br>Desenvolvedor: %s".formatted(developer.getName());
        return bodyClient + bodyDeveloper;
    }
}
