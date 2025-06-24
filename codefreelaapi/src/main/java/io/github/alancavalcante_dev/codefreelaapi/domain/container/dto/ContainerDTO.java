package io.github.alancavalcante_dev.codefreelaapi.domain.container.dto;

import lombok.AllArgsConstructor;

import java.time.LocalDate;


@AllArgsConstructor
public class ContainerDTO {

    String idContainer;

    String title;

    String description;

    LocalDate closingDate;

}
