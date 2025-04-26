package io.github.alancavalcante_dev.codefreelaapi.presentation.dto.projectbusiness;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDate;

@AllArgsConstructor
@Getter
public class ProjectBusinessResponseDTO {

    String idProjectBusiness;
    String title;
    String description;
    boolean confirmation;
    LocalDate closingDate;
    LocalDate dateMatch;

}
