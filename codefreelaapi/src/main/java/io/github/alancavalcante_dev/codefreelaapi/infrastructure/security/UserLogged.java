package io.github.alancavalcante_dev.codefreelaapi.infrastructure.security;

import io.github.alancavalcante_dev.codefreelaapi.domain.user.User;
import org.springframework.security.core.context.SecurityContextHolder;

public class UserLogged {

    public static User load() {
        return (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }
}
