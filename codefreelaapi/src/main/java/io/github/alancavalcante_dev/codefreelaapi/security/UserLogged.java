package io.github.alancavalcante_dev.codefreelaapi.security;

import io.github.alancavalcante_dev.codefreelaapi.model.User;
import org.springframework.security.core.context.SecurityContextHolder;

public class UserLogged {

    public static User load() {
        return (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }
}
