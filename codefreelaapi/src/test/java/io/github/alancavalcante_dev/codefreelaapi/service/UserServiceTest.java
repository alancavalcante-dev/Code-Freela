package io.github.alancavalcante_dev.codefreelaapi.service;

import io.github.alancavalcante_dev.codefreelaapi.model.Profile;
import io.github.alancavalcante_dev.codefreelaapi.model.User;
import io.github.alancavalcante_dev.codefreelaapi.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.UUID;

@Slf4j
@SpringBootTest
public class UserServiceTest {

    @Autowired
    private UserRepository repository;



    @Test
    public void saveUser() {
        User user = new User();
        user.setIdUser(UUID.randomUUID());
        user.setUsername("beterraba");
        user.setPassword("$2a$10$G94f0Xdjc7nn9Zuf5xqPMeAIlJKHBb8pTEsNucg3Pov1cKiApgMlO");
        user.setEmail("beterraba@example.com");
        user.setRoles(List.of("OPERADOR"));
        user.setActive(false);
        repository.save(user);

        log.info("Usuário Teste Criado! User: {}", user);
    }
}
