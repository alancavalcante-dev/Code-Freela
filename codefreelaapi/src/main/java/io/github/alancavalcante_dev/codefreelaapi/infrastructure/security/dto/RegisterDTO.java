package io.github.alancavalcante_dev.codefreelaapi.infrastructure.security.dto;

import io.github.alancavalcante_dev.codefreelaapi.domain.entity.enums.UserRole;

public record RegisterDTO(String login, String password, UserRole role) {
}
