package io.github.alancavalcante_dev.codefreelaapi.repository;

import io.github.alancavalcante_dev.codefreelaapi.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import io.github.alancavalcante_dev.codefreelaapi.model.Profile;

@Repository
public interface ProfileRepository extends JpaRepository<Profile, UUID> {

    Optional<Profile> findByCpf(String profile);

    Optional<Profile> findByEmail(String profile);

//    @Query("select * from Profile as p where p.User = :uuid;")
    Optional<Profile> findByUser(User user);

}
