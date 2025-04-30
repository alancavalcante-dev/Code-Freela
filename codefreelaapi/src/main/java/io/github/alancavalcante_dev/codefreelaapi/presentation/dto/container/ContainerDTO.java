package io.github.alancavalcante_dev.codefreelaapi.presentation.dto.container;

import lombok.AllArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;


@AllArgsConstructor
public class ContainerDTO {

    String idContainer;

    String title;

    String description;

    LocalDate closingDate;

}
