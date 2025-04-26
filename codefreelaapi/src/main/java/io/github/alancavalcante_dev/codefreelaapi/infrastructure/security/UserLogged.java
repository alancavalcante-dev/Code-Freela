package io.github.alancavalcante_dev.codefreelaapi.infrastructure.security;

import io.github.alancavalcante_dev.codefreelaapi.domain.user.User;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

@Component
public class UserLogged {

    public User load() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.getPrincipal() instanceof User) {
            return (User) authentication.getPrincipal();
        }
        throw new UsernameNotFoundException("Usuário não autenticado");
    }
}
