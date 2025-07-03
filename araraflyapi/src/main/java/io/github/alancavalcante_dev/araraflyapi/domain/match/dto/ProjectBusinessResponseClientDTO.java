package io.github.alancavalcante_dev.araraflyapi.domain.match.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDate;


@AllArgsConstructor
@Getter
public class ProjectBusinessResponseClientDTO {

    String idProjectBusiness;
    String title;
    String description;
    boolean confirmationClient;
    boolean confirmationDeveloper;
    String idDeveloper;
    LocalDate closingDate;
    LocalDate dateMatch;

}
