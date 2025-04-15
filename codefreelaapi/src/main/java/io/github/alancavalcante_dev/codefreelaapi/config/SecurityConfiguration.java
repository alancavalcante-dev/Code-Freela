package io.github.alancavalcante_dev.codefreelaapi.config;


import io.github.alancavalcante_dev.codefreelaapi.security.CustomAuthenticationProvider;
import io.github.alancavalcante_dev.codefreelaapi.security.CustomizeUserDetailsService;
import io.github.alancavalcante_dev.codefreelaapi.security.JwtCustomAuthenticationFilter;
import io.github.alancavalcante_dev.codefreelaapi.security.LoginSocialSuccessHandler;
import io.github.alancavalcante_dev.codefreelaapi.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.core.GrantedAuthorityDefaults;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;
import org.springframework.security.oauth2.server.resource.web.authentication.BearerTokenAuthenticationFilter;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.stereotype.Component;

@Component
@EnableWebSecurity
@EnableMethodSecurity(securedEnabled = true, jsr250Enabled = true)
public class SecurityConfiguration {

    @Bean
    public SecurityFilterChain securityFilterChain(
            JwtCustomAuthenticationFilter jwtCustomAuthenticationFilter,
            HttpSecurity httpSecurity,
            LoginSocialSuccessHandler loginSuccessHandlerCustom) throws Exception {
        return httpSecurity
                .csrf(AbstractHttpConfigurer::disable)
                .httpBasic(Customizer.withDefaults()) // autenticação via aplicações
                .formLogin(formConfig -> { // autenticação via browser
                    formConfig.loginPage("/login").permitAll();
                })
                .authorizeHttpRequests(authorize -> { // configurações de rotas e permissões
                    authorize.requestMatchers("/login/**").permitAll();
                    authorize.requestMatchers(HttpMethod.POST, "api/users/**").permitAll(); // registro de usuario
//                    authorize.requestMatchers("/**").hasRole("ADMIN");
                    authorize.anyRequest().authenticated();
                })
                .oauth2Login(oauth -> {
                    oauth.loginPage("/login");
                    oauth.successHandler(loginSuccessHandlerCustom);
                })
                .oauth2ResourceServer(oauth2RS -> oauth2RS.jwt(Customizer.withDefaults()))
                .addFilterAfter(jwtCustomAuthenticationFilter, BearerTokenAuthenticationFilter.class)
                .build();
    }


    @Bean
    public WebSecurityCustomizer webSecurityCustomizer(){
        return web -> web.ignoring().requestMatchers(
                "/v2/api-docs/**",
                "/v3/api-docs/**",
                "/swagger-resources/**",
                "/swagger-ui.html",
                "/swagger-ui/**",
                "/webjars/**",
                "/actuator/**"
        );
    }



    // definindo prefixo da role, tirando "ROLE_"
    // Configura, no Http Basic, o prefixo ROLE

    @Bean
    public GrantedAuthorityDefaults grantedAuthorityDefaults() {
        return new GrantedAuthorityDefaults("");
    }


    // SCOPE_GERENTE = padrão
    // Configura, no token Jwt, o prefixo SCOPE
    @Bean
    public JwtAuthenticationConverter jwtAuthenticationConverter() {
        var authoritiesConverter = new JwtGrantedAuthoritiesConverter();
        authoritiesConverter.setAuthorityPrefix("");

        var converter = new JwtAuthenticationConverter();
        converter.setJwtGrantedAuthoritiesConverter(authoritiesConverter);

        return converter;
    }
}
