package io.github.alancavalcante_dev.codefreelaapi.presentation.dto.projectbusiness;

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
