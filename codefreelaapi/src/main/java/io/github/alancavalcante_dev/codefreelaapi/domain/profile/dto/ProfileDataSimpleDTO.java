package io.github.alancavalcante_dev.codefreelaapi.domain.profile.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;

public record ProfileDataSimpleDTO(
        String name,
        String email,
        LocalDate dateCreated
) {
}
