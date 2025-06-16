package io.github.alancavalcante_dev.codefreelaapi.domain.container;

import io.github.alancavalcante_dev.codefreelaapi.domain.entity.Container;
import io.github.alancavalcante_dev.codefreelaapi.domain.entity.Profile;
import io.github.alancavalcante_dev.codefreelaapi.domain.entity.ProjectBusiness;
import io.github.alancavalcante_dev.codefreelaapi.domain.entity.enums.StateBusiness;
import io.github.alancavalcante_dev.codefreelaapi.domain.entity.enums.StateProject;
import io.github.alancavalcante_dev.codefreelaapi.domain.gitea.GiteaService;
import io.github.alancavalcante_dev.codefreelaapi.infrastructure.repository.ContainerRepository;
import io.github.alancavalcante_dev.codefreelaapi.infrastructure.repository.ProfileRepository;
import io.github.alancavalcante_dev.codefreelaapi.infrastructure.repository.ProjectBusinessRepository;
import jakarta.transaction.Transactional;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Getter
@Setter
public class CreateContainerProduction {

    private ContainerRepository containerRepository;
    private ProfileRepository profileRepository;
    private ProjectBusinessRepository projectBusinessRepository;
    private GiteaService giteaService;
    private ProjectBusiness project;


    public CreateContainerProduction(
            ContainerRepository containerRepository,
            ProfileRepository profileRepository,
            ProjectBusinessRepository projectBusinessRepository,
            GiteaService giteaService,
            ProjectBusiness project
    ) {
        this.containerRepository = containerRepository;
        this.profileRepository = profileRepository;
        this.projectBusinessRepository = projectBusinessRepository;
        this.giteaService = giteaService;
        this.project = project;
    }


    @Transactional
    public void create() {
        Container container = new Container();

        profileRepository.getByIdUser(project.getProject().getUser().getId())
                .orElseThrow( () -> new RuntimeException("Perfil do usuário inexistente") );

        Profile developer = profileRepository.getByIdUser(project.getUserDeveloper().getId())
                .orElseThrow( () -> new RuntimeException("Perfil do usuário inexistente") );

        project.getProject().setStateBusiness(StateBusiness.WORKING);
        projectBusinessRepository.save(project);

        container.setProjectBusiness(project);
        container.setStateProject(StateProject.DIDNTSTART);
        Container containerSaved = containerRepository.save(container);
        log.info("Container do projeto criado");

        giteaService.createRepository(developer, project.getProject(), containerSaved);
    }
}
