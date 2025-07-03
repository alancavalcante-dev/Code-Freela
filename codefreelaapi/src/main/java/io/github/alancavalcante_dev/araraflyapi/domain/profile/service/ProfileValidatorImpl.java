package io.github.alancavalcante_dev.araraflyapi.domain.profile.service;

import io.github.alancavalcante_dev.araraflyapi.commom.validate.Validate;
import io.github.alancavalcante_dev.araraflyapi.domain.portfolio.entity.PortfolioDeveloper;
import io.github.alancavalcante_dev.araraflyapi.domain.profile.entity.Profile;
import io.github.alancavalcante_dev.araraflyapi.exceptions.CpfExistsException;
import io.github.alancavalcante_dev.araraflyapi.exceptions.EmailExistsException;
import io.github.alancavalcante_dev.araraflyapi.infrastructure.repository.PortfolioDeveloperRepository;
import io.github.alancavalcante_dev.araraflyapi.infrastructure.repository.ProfileRepository;
import io.github.alancavalcante_dev.araraflyapi.infrastructure.security.UserLogged;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Slf4j
@Component
public class ProfileValidatorImpl implements Validate<Profile> {


    @Autowired
    private ProfileRepository profileRepository;

    @Autowired
    private PortfolioDeveloperRepository portfolioDeveloperRepository;

    @Autowired
    private UserLogged logged;


    @Override
    public void validate(Profile profile) {
        validatorEmailExists(profile);
        validatorCpfExists(profile);
        validatorCreatePortfolioUserDeveloper(profile);
    }


    public void validatorEmailExists(Profile profile) {
        Optional<Profile> profileOptionalEmail = profileRepository.findByEmail(profile.getEmail());
        if (profileOptionalEmail.isPresent()) {
            throw new EmailExistsException("Já existe um perfil com esse Email no nosso sistema!");
        }
    }


    public void validatorCpfExists(Profile profile) {
        Optional<Profile> profileOptionalCpf = profileRepository.findByCpf(profile.getCpf());
        if (profileOptionalCpf.isPresent()) {
            throw new CpfExistsException("Já existe um perfil com esse CPF no nosso sistema!");
        }
    }


    @Transactional
    public void validatorCreatePortfolioUserDeveloper(Profile profile) {
        if (profile.getIsDeveloper()) {
            Optional<PortfolioDeveloper> portOpt = portfolioDeveloperRepository.getPortfolioDeveloperByIdUser(logged.load().getId());

            if (portOpt.isEmpty()) {
                if (profile.getIsDeveloper()) {
                    PortfolioDeveloper portDev = new PortfolioDeveloper();
                    portDev.setPresentation("Olá, me chamo " + profile.getName());
                    portDev.setResume("Seja bem-vindo ao meu portfólio de teste");
                    portDev.setProfile(profile);
                    portDev.setUser(profile.getUser());

                    portfolioDeveloperRepository.save(portDev);
                    log.info("Portfólio do usuário criado");
                }
            }
        }

    }
}


