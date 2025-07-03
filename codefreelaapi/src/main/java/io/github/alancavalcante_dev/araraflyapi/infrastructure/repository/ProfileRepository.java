package io.github.alancavalcante_dev.araraflyapi.infrastructure.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;
import io.github.alancavalcante_dev.araraflyapi.domain.profile.entity.Profile;

@Repository
public interface ProfileRepository extends JpaRepository<Profile, UUID> {

    Optional<Profile> findByCpf(String profile);

    Optional<Profile> findByEmail(String profile);

    @Query("select p from Profile p where p.user.id = :idUser")
    Optional<Profile> getByIdUser(UUID idUser);

}
