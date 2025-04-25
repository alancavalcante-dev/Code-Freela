package io.github.alancavalcante_dev.codefreelaapi.domain.project;

import io.github.alancavalcante_dev.codefreelaapi.domain.entity.Project;
import io.github.alancavalcante_dev.codefreelaapi.domain.user.User;
import io.github.alancavalcante_dev.codefreelaapi.exceptions.CurrentDateGreaterThanProjectDate;
import io.github.alancavalcante_dev.codefreelaapi.exceptions.SomeValueMustBeFilled;
import io.github.alancavalcante_dev.codefreelaapi.infrastructure.repository.ProjectRepository;
import io.github.alancavalcante_dev.codefreelaapi.infrastructure.security.UserLogged;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ProjectService {


    private final ProjectRepository repository;


    public List<Project> getAllProjects() {
        return repository.findAll();
    }


    public Project register(Project project) {
        User user = UserLogged.load();
        project.setUser(user);

        projectsOpen(project);
        fieldsPrice(project);
        fieldDateClosing(project.getClosingDate());

        return repository.save(project);
    }


    public void projectsOpen(Project project) {
        User user = project.getUser();
        List<Project> projects = repository.getProjectsOpenByUser(UUID.fromString(user.getId()));

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
}
