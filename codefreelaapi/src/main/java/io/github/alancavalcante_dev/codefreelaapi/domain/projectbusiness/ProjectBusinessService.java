package io.github.alancavalcante_dev.codefreelaapi.domain.projectbusiness;

import io.github.alancavalcante_dev.codefreelaapi.domain.entity.Container;
import io.github.alancavalcante_dev.codefreelaapi.domain.entity.ProjectBusiness;
import io.github.alancavalcante_dev.codefreelaapi.domain.entity.User;
import io.github.alancavalcante_dev.codefreelaapi.infrastructure.repository.ContainerRepository;
import io.github.alancavalcante_dev.codefreelaapi.infrastructure.repository.ProjectBusinessRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ProjectBusinessService {


    private final ProjectBusinessRepository repository;
    private final ContainerRepository containerRepository;


    public Optional<ProjectBusiness> getByIdProjectBusiness(UUID uuid) {
        return repository.findById(uuid);
    }


    public List<ProjectBusiness> getAllMatchesBusinessUserDeveloper(User user) {
        return repository.getAllByIdUserDeveloper(user.getId());
    }


    public List<ProjectBusiness> getAllMatchesBusinessUserClient(User user) {
        return repository.getAllByIdUserClient(user.getId());
    }


    @Transactional
    public ProjectBusiness register(ProjectBusiness project) {
        validatorRegister(project);
        return repository.save(project);
    }


    @Transactional
    public ProjectBusiness update(ProjectBusiness project) {
        validatorUpdate(project);
        return repository.save(project);
    }



    @Transactional
    public void delete(ProjectBusiness project) {
        validatorDelete(project);
        repository.delete(project);
    }


    public void validatorRegister(ProjectBusiness project) {
        validateClientVotedOnOwnBusiness(project);
        validateDeveloperQuantityProjectsIsConfirm(project);
        validateMatchesDuplicates(project);
        validateMatchesConfirmationCreateContainerProduction(project);
    }


    public void validatorUpdate(ProjectBusiness project) {
        validateClientVotedOnOwnBusiness(project);
        validateDeveloperQuantityProjectsIsConfirm(project);
        validateMatchesConfirmationCreateContainerProduction(project);
    }


    public void validatorDelete(ProjectBusiness project) {
        Optional<Container> containerOpt = containerRepository.findByProjectBusiness(project);
        if (containerOpt.isEmpty()) {
            throw new RuntimeException("Não existe esse container vínculado com um negociamento feito!");
        }
        throw new RuntimeException("Não é possível excluir um Negociamento que já está em desenvolvimento!");
    }


    @Transactional
    public void validateMatchesConfirmationCreateContainerProduction(ProjectBusiness project) {
        if (project.isConfirmDeveloper() && project.isConfirmClient()) {
            Container container = new Container();
            container.setProjectBusiness(project);
            containerRepository.save(container);
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
