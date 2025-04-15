package io.github.alancavalcante_dev.codefreelaapi.security;

import io.github.alancavalcante_dev.codefreelaapi.model.User;
import io.github.alancavalcante_dev.codefreelaapi.service.UserService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

/*
    Essa classe serve para gente customizar nossas roles. Para passar o prefixo corrigido.
    Quando retornado o user, ele é autenticado.
 */

@RequiredArgsConstructor
@Getter
public class CustomAuthentication implements Authentication {
    // Carrega o usuário que está logado

    private final User user;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        // autoriza o usuário, pega as permissões da base de dados

        return user.getRoles().stream()
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());
    }

    @Override
    public Object getCredentials() {
        return null;
    }

    @Override
    public Object getDetails() {
        return user;
    }

    @Override
    public Object getPrincipal() {
        return user;
    }

    @Override
    public boolean isAuthenticated() {
        return true;
    }

    @Override
    public void setAuthenticated(boolean isAuthenticated) throws IllegalArgumentException {

    }

    @Override
    public String getName() {
        return user.getUsername();
    }
}
