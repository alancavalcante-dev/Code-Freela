package io.github.alancavalcante_dev.codefreelaapi.domain.project.service;

import io.github.alancavalcante_dev.codefreelaapi.domain.project.entity.Project;
import io.github.alancavalcante_dev.codefreelaapi.domain.entity.enums.StateBusiness;
import io.github.alancavalcante_dev.codefreelaapi.domain.entity.User;
import io.github.alancavalcante_dev.codefreelaapi.exceptions.CurrentDateGreaterThanProjectDate;
import io.github.alancavalcante_dev.codefreelaapi.exceptions.SomeValueMustBeFilled;
import io.github.alancavalcante_dev.codefreelaapi.infrastructure.repository.ProjectRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ProjectService {


    private final ProjectRepository repository;


    public Optional<Project> getProjectById(UUID IdProject) {
        return repository.findById(IdProject);
    }


    public Optional<Project> getProjectByIdProjectByUserId(UUID idUser, UUID IdProject) {
        return repository.getProjectByIdProjectByUserId(idUser, IdProject);
    }


    public List<Project> getProjectsByUserForStateBusiness(User user, StateBusiness state) {
        return repository.getProjectsByUserForStateBusiness(user.getId(), state);
    }


    public Page<Project> findAllWithPage(Specification<Project> spec, Pageable page){
        return repository.findAll(spec, page);
    }


    @Transactional
    public Project save(Project project) {
        projectsOpen(project);
        fieldsPrice(project);
        fieldDateClosing(project.getClosingDate());
        projectsEquals(project);

        return repository.save(project);
    }

    @Transactional
    public Project update(Project project) {
        fieldsPrice(project);
        fieldDateClosing(project.getClosingDate());
        return repository.save(project);
    }


    @Transactional
    public void delete(Project project) {
        UUID idUser = project.getUser().getId();
        UUID idProject = project.getIdProject();

        Optional<Project> projectOptional = repository.getStateBusinessWorking(idUser, idProject);

        if (projectOptional.isPresent()) {
            throw new RuntimeException("Não é possível excluir esse projeto pois já foi iniciado!");
        }
        repository.delete(project);
    }


    public void projectsOpen(Project project) {
        User user = project.getUser();
        List<Project> projects = getProjectsByUserForStateBusiness(user, StateBusiness.OPEN);

        if (projects.size() >= 2) {
            throw new RuntimeException("Estourou o limite de projetos por usuário!");
        }
    }


    public void fieldsPrice(Project project) {
        BigDecimal priceDay = project.getPriceDay() != null ? project.getPriceDay() : BigDecimal.ZERO;
        BigDecimal priceHour = project.getPriceHour() != null ? project.getPriceHour() : BigDecimal.ZERO;
        BigDecimal priceProject = project.getPriceProject() != null ? project.getPriceProject() : BigDecimal.ZERO;

        boolean priceDayValid = priceDay.compareTo(BigDecimal.ZERO) > 0;
        boolean priceHourValid = priceHour.compareTo(BigDecimal.ZERO) > 0;
        boolean priceProjectValid = priceProject.compareTo(BigDecimal.ZERO) > 0;

        int validPrices = 0;
        if (priceDayValid) validPrices++;
        if (priceHourValid) validPrices++;
        if (priceProjectValid) validPrices++;

        if (validPrices == 0) {
            throw new SomeValueMustBeFilled(
                    "Algum valor deve ser preenchido em algum tipo de preço: Preço por dia, Preço por Hora, Preço por Projeto"
            );
        }

        if (validPrices > 1) {
            throw new RuntimeException(
                    "Somente um tipo de preço deve ser preenchido: escolha entre Preço por dia, hora ou projeto."
            );
        }
    }


    public void fieldDateClosing(LocalDate date) {
        LocalDate dateCurrent = LocalDate.now();
        if (dateCurrent.isAfter(date)) {
            throw new CurrentDateGreaterThanProjectDate(
                    "Data do projeto deve ser maior que a data atual"
            );
        }
    }

    public void projectsEquals(Project project) {
        List<Project> projects = getProjectsByUserForStateBusiness(project.getUser(), StateBusiness.OPEN);
        System.out.println(projects);

        if (!projects.isEmpty()) {
            Project projGet = projects.getFirst();

            if (project.getTitle().equals(projGet.getTitle()) || project.getDescription().equals(projGet.getDescription())) {
                throw new RuntimeException("Já existe um projeto com o mesmo nome ou descrição");
            }
        }
    }
}
