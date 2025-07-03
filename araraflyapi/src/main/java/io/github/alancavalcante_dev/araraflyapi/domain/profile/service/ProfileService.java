package io.github.alancavalcante_dev.araraflyapi.domain.profile.service;


import io.github.alancavalcante_dev.araraflyapi.domain.profile.entity.Profile;
import io.github.alancavalcante_dev.araraflyapi.domain.entity.User;
import io.github.alancavalcante_dev.araraflyapi.infrastructure.repository.ProfileRepository;
import io.github.alancavalcante_dev.araraflyapi.infrastructure.security.UserLogged;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProfileService {


    private final ProfileRepository profileRepository;
    private final ProfileValidatorImpl profileValidate;
    private final UserLogged logged;


    public Optional<Profile> getProfileByIdUser(User user) {
        return profileRepository.getByIdUser(user.getId());
    }


    @Transactional
    public Profile register(Profile profile) {
        profileValidate.validate(profile);
        profile.setUser(logged.load());
        profile.setBalance(BigDecimal.valueOf(0.0));
        return profileRepository.save(profile);
    }


    @Transactional
    public Profile update(Profile profile) {
        profileValidate.validatorCreatePortfolioUserDeveloper(profile);
        return profileRepository.save(profile);
    }
}

