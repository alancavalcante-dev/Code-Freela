package io.github.alancavalcante_dev.codefreelaapi.presentation.dto.user;

import io.github.alancavalcante_dev.codefreelaapi.domain.entity.enums.UserRole;

public record RegisterDTO(String login, String password, UserRole role) {
}
