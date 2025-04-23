package io.github.alancavalcante_dev.codefreelaapi.repository;

import io.github.alancavalcante_dev.codefreelaapi.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;

public interface UserRepository extends JpaRepository<User, String> {
    UserDetails findByLogin(String login);
}