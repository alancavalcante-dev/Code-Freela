package io.github.alancavalcante_dev.araraflyapi.domain.match.dto;

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
