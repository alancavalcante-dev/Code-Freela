package io.github.alancavalcante_dev.araraflyapi.domain.profile.dto;

import java.time.LocalDate;

public record ProfileDataSimpleDTO(
        String name,
        String email,
        LocalDate dateCreated
) {
}
