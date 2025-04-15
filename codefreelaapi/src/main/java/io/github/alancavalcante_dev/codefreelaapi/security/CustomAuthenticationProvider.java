package io.github.alancavalcante_dev.codefreelaapi.security;

import io.github.alancavalcante_dev.codefreelaapi.model.User;
import io.github.alancavalcante_dev.codefreelaapi.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Optional;

/*
    Essa classe recebe UsernamePasswordAuthenticationToken e verifica se existe na nossa base
    de dados, se as credenciais baterem, o usuário é autenticado, se não for o caso, gera um erro.
*/
@Component
@RequiredArgsConstructor
public class CustomAuthenticationProvider implements AuthenticationProvider {

    private final UserService userService;
    private final PasswordEncoder encoder;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String login = authentication.getName();
        String passwordEntered = authentication.getCredentials().toString();

        Optional<User> userGet = userService.getUserByLogin(login);
        if (userGet.isEmpty()) {
            throw getErroUserNotFound();
        }
        User user = userGet.get();
        String passwordEncryption = user.getPassword();

        boolean passwordMatch = encoder.matches(passwordEntered, passwordEncryption);

        if (passwordMatch) {
            return new CustomAuthentication(user);
        }

        throw getErroUserNotFound();
    }

    private UsernameNotFoundException getErroUserNotFound() {
        return new UsernameNotFoundException("Usuário e/ou senha incorretas");
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.isAssignableFrom(UsernamePasswordAuthenticationToken.class);
    }
}
