package io.github.alancavalcante_dev.codefreelaapi.domain.profile;

import io.github.alancavalcante_dev.codefreelaapi.domain.core.Validate;
import io.github.alancavalcante_dev.codefreelaapi.domain.entity.PortfolioDeveloper;
import io.github.alancavalcante_dev.codefreelaapi.domain.entity.Profile;
import io.github.alancavalcante_dev.codefreelaapi.exceptions.CpfExistsException;
import io.github.alancavalcante_dev.codefreelaapi.exceptions.EmailExistsException;
import io.github.alancavalcante_dev.codefreelaapi.infrastructure.repository.PortfolioDeveloperRepository;
import io.github.alancavalcante_dev.codefreelaapi.infrastructure.repository.ProfileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class ProfileValidatorImpl implements Validate<Profile> {


    @Autowired
    private ProfileRepository profileRepository;

    @Autowired
    private PortfolioDeveloperRepository portfolioDeveloperRepository;


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


    public void validatorCreatePortfolioUserDeveloper(Profile profile) {
        if (profile.getIsDeveloper()) {
            PortfolioDeveloper portDev = new PortfolioDeveloper();
            portDev.setPresentation("Olá, me chamo " + profile.getName());
            portDev.setResume("Seja bem-vindo ao meu portfólio de teste");
            portDev.setUser(profile.getUser());
            portfolioDeveloperRepository.save(portDev);
        }
    }
}


