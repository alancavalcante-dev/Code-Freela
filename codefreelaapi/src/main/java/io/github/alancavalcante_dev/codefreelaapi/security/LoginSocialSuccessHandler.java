package io.github.alancavalcante_dev.codefreelaapi.security;

import io.github.alancavalcante_dev.codefreelaapi.model.User;
import io.github.alancavalcante_dev.codefreelaapi.service.UserService;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;


import java.io.IOException;
import java.util.List;
import java.util.Optional;


@Component
@Slf4j
@RequiredArgsConstructor
public class LoginSocialSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {

    private static final String SENHA_PADRAO = "321";

    private final UserService usuarioService;

    @Override
    public void onAuthenticationSuccess(
            HttpServletRequest request,
            HttpServletResponse response,
            Authentication authentication) throws ServletException, IOException {

        OAuth2AuthenticationToken auth2AuthenticationToken = (OAuth2AuthenticationToken) authentication;
        OAuth2User oAuth2User = auth2AuthenticationToken.getPrincipal();

        String email = oAuth2User.getAttribute("email");

        Optional<User> usuario = usuarioService.getUserByLogin(email);

        if(usuario.isEmpty()){
            cadastrarUsuarioNaBase(email);
        }

        User user = usuario.get();

        authentication = new CustomAuthentication(user);

        SecurityContextHolder.getContext().setAuthentication(authentication);

        super.onAuthenticationSuccess(request, response, authentication);
    }

    private void cadastrarUsuarioNaBase(String email) {
        User usuario;
        usuario = new User();
        usuario.setEmail(email);

        usuario.setUsername(obterLoginApartirEmail(email));

        usuario.setPassword(SENHA_PADRAO);
        usuario.setRoles(List.of("OPERADOR"));
        log.info("Usuário salvo com sucesso!");
        usuarioService.save(usuario);
    }

    private String obterLoginApartirEmail(String email) {
        return email.substring(0, email.indexOf("@"));
    }
}