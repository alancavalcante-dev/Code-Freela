package io.github.alancavalcante_dev.codefreelaapi.presentation.dto.container;

import io.github.alancavalcante_dev.codefreelaapi.domain.entity.enums.StateBusiness;
import io.github.alancavalcante_dev.codefreelaapi.domain.entity.enums.StateProject;
import lombok.AllArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@AllArgsConstructor
@Setter
public class ContainerDetailsDTO {

    String id;

    String title;

    String description;

    StateBusiness stateBusiness;

    LocalDate closingDateProject;

    LocalDate closingCreatedContainer;

    StateProject stateProject;

    LocalDateTime lastDeploy;

}
