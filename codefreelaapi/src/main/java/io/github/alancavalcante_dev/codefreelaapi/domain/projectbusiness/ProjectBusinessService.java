package io.github.alancavalcante_dev.codefreelaapi.domain.projectbusiness;

import io.github.alancavalcante_dev.codefreelaapi.domain.entity.ProjectBusiness;
import io.github.alancavalcante_dev.codefreelaapi.domain.entity.User;
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


    public Optional<ProjectBusiness> getByIdProjectBusiness(UUID uuid) {
        return repository.findById(uuid);
    }


    public List<ProjectBusiness> getAllMatchesBusiness(User user) {
        return repository.getAllByIdUser(user.getId());
    }


    @Transactional
    public ProjectBusiness save(ProjectBusiness project) {
        validateClientVotedOnOwnBusiness(project);
        validateDeveloperQuantityProjectsIsConfirm(project);

        return repository.save(project);
    }


    @Transactional
    public void delete(ProjectBusiness project) {
        repository.delete(project);
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
        List<ProjectBusiness> projects = repository.getAllByIdUserIsConfirm(user.getId());

        if (projects.size() >= 2) {
            throw new RuntimeException("Estourou o limite de projetos por desenvolvedor!");
        }
    }
}
