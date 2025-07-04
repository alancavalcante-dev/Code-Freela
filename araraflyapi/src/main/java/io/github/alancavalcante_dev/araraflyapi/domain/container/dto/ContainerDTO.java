package io.github.alancavalcante_dev.araraflyapi.domain.container.dto;

import java.time.LocalDate;



public record ContainerDTO(
        String idContainer,

        String title,

        String description,

        LocalDate closingDate
) {}
