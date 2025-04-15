package io.github.alancavalcante_dev.codefreelaapi.security;

import io.github.alancavalcante_dev.codefreelaapi.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

@RequiredArgsConstructor
public class CustomizeUserDetailsService implements UserDetailsService {

    private final UserService userService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        var userByLogin = userService.getUserByLogin(username);

        if (userByLogin.isEmpty()) throw new UsernameNotFoundException("Usuário não encontrado!");
        var user = userByLogin.get();

        return User.builder()
                .username(user.getUsername())
                .password(user.getPassword())
                .roles(user.getRoles().toArray(new String[user.getRoles().size()]))
                .build();
    }
}

