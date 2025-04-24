package io.github.alancavalcante_dev.codefreelaapi.domain.profile;


import io.github.alancavalcante_dev.codefreelaapi.domain.entity.Profile;
import io.github.alancavalcante_dev.codefreelaapi.domain.user.User;
import io.github.alancavalcante_dev.codefreelaapi.infrastructure.repository.ProfileRepository;
import io.github.alancavalcante_dev.codefreelaapi.infrastructure.security.UserLogged;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ProfileService {


    private final ProfileRepository profileRepository;
    private final ProfileValidatorImpl profileValidate;


    public List<Profile> getAllProfiles() {
        return profileRepository.findAll();
    }

    public Optional<Profile> getByIdProfile(UUID uuid) {
        return profileRepository.findById(uuid);
    }

    public Optional<Profile> getProfileByIdUser(User user) {
        return profileRepository.findByUser(user);
    }


    @Transactional
    public Profile register(Profile profile) {
        profileValidate.validate(profile);
        profile.setUser(UserLogged.load());
        profile.setBalance(BigDecimal.valueOf(0.0));
        return profileRepository.save(profile);
    }


    @Transactional
    public Profile update(Profile profile) {
        return profileRepository.save(profile);
    }


    @Transactional
    public void delete(Profile profile) {
        profileRepository.delete(profile);
    }

}

