package io.github.alancavalcante_dev.codefreelaapi.repository;

import io.github.alancavalcante_dev.codefreelaapi.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends JpaRepository<User, UUID> {

    @Query("SELECT u FROM User u WHERE u.username = ?1 and u.idUser <> ?2")
    Optional<User> findByUsernameDistinctId(String username, UUID idUser);

    Optional<User> findByUsername(String username);

    User findByEmail(String email);

}