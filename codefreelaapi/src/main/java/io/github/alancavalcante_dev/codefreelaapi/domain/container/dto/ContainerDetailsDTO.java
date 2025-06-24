package io.github.alancavalcante_dev.codefreelaapi.domain.container.dto;

import io.github.alancavalcante_dev.codefreelaapi.domain.entity.enums.StateBusiness;
import io.github.alancavalcante_dev.codefreelaapi.domain.entity.enums.StateProject;
import lombok.AllArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@AllArgsConstructor
@Setter
public class ContainerDetailsDTO {

    String id;

    String title;

    String description;

    StateBusiness stateBusiness;

    LocalDate dateCreatedContainer;

    StateProject stateProject;

}
