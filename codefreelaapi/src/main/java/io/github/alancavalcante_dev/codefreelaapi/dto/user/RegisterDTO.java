package io.github.alancavalcante_dev.codefreelaapi.dto.user;

import io.github.alancavalcante_dev.codefreelaapi.model.UserRole;

public record RegisterDTO(String login, String password, UserRole role) {
}
