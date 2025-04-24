package io.github.alancavalcante_dev.codefreelaapi.infrastructure.repository;

import io.github.alancavalcante_dev.codefreelaapi.domain.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;
import io.github.alancavalcante_dev.codefreelaapi.domain.entity.Profile;

@Repository
public interface ProfileRepository extends JpaRepository<Profile, UUID> {

    Optional<Profile> findByCpf(String profile);

    Optional<Profile> findByEmail(String profile);

//    @Query("select * from Profile as p where p.User = :uuid;")
    Optional<Profile> findByUser(User user);

}
