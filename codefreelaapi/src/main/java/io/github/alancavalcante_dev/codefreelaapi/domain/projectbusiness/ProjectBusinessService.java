package io.github.alancavalcante_dev.codefreelaapi.domain.projectbusiness;

import io.github.alancavalcante_dev.codefreelaapi.domain.entity.ProjectBusiness;
import io.github.alancavalcante_dev.codefreelaapi.domain.entity.User;
import io.github.alancavalcante_dev.codefreelaapi.domain.notification.NotificationEmailSender;
import io.github.alancavalcante_dev.codefreelaapi.infrastructure.repository.ContainerRepository;
import io.github.alancavalcante_dev.codefreelaapi.infrastructure.repository.ProfileRepository;
import io.github.alancavalcante_dev.codefreelaapi.infrastructure.repository.ProjectBusinessRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProjectBusinessService {


    private final ProjectBusinessRepository repository;
    private final ContainerRepository containerRepository;
    private final ProfileRepository profileRepository;
    private final ProjectBusinessRepository projectBusinessRepository;
    private final NotificationEmailSender notification;



    public Optional<ProjectBusiness> getByIdProjectBusiness(UUID uuid) {
        return repository.findById(uuid);
    }

    public Optional<ProjectBusiness> getByIdProjectBusinessForUserDeveloper(UUID idUser, UUID idProjectBusiness){
        return repository.getByIdProjectBusinessForUserDeveloper(idUser, idProjectBusiness);
    }

    public Optional<ProjectBusiness> getByIdProjectBusinessForUserClient(UUID idUser, UUID idProjectBusiness){
        return repository.getByIdProjectBusinessForUserClient(idUser, idProjectBusiness);
    }

    public List<ProjectBusiness> getAllMatchesBusinessUserDeveloper(User user) {
        return repository.getAllByIdUserDeveloper(user.getId());
    }

    public List<ProjectBusiness> getAllMatchesBusinessUserClient(User user) {
        return repository.getAllByIdUserClient(user.getId());
    }


    @Transactional
    public void register(ProjectBusiness project) {
        // Se for registro verifica se existe Match duplicado

        validateClientVotedOnOwnBusiness(project);
        validateDeveloperQuantityProjectsIsConfirm(project);
        validateMatchesDuplicates(project);
        validateMatchesConfirmationCreateContainerProduction(project);
        repository.save(project);
    }


    @Transactional
    public ProjectBusiness update(ProjectBusiness project) {
        // Se for atualização não verifica se existe Match duplicado

        validateClientVotedOnOwnBusiness(project);
        validateDeveloperQuantityProjectsIsConfirm(project);
        validateMatchesConfirmationCreateContainerProduction(project);
        return repository.save(project);
    }


    @Transactional
    public void delete(ProjectBusiness project) {
        repository.delete(project);
    }


    @Transactional
    public void validateMatchesConfirmationCreateContainerProduction(ProjectBusiness project) {
        if (project.isConfirmDeveloper() && project.isConfirmClient()) {
            CreateContainerProduction containerProduction = new CreateContainerProduction(
                    containerRepository,
                    profileRepository,
                    projectBusinessRepository,
                    project
            );
            containerProduction.create();
        }
    }


    public void validateMatchesDuplicates(ProjectBusiness project) {
        List<ProjectBusiness> projectBusinessList = getAllMatchesBusinessUserDeveloper(project.getUserDeveloper());
        List<ProjectBusiness> filter = projectBusinessList.stream()
                .filter(p -> p.getIdProjectBusiness() == project.getIdProjectBusiness()).toList();
        if (!filter.isEmpty()) {
            throw new RuntimeException("Não pode dar mais de 1 match no mesmo projeto");
        }
    }


    public void validateClientVotedOnOwnBusiness(ProjectBusiness project) {
        User userDeveloper = project.getUserDeveloper();
        User userProject = project.getProject().getUser();

        if (userDeveloper.getId().equals(userProject.getId())) {
            throw new RuntimeException("Não pode dar Match no próprio projeto!");
        }
    }


    public void validateDeveloperQuantityProjectsIsConfirm(ProjectBusiness project) {
        User user = project.getUserDeveloper();
        if (project.isConfirmDeveloper()) {
            List<ProjectBusiness> projects = repository.getAllByIdUserIsConfirm(user.getId());

            if (projects.size() >= 2) {
                throw new RuntimeException("Estourou o limite de projetos por desenvolvedor!");
            }
        }
    }
}
