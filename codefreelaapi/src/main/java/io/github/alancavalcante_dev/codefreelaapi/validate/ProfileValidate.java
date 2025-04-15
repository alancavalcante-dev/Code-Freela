package io.github.alancavalcante_dev.codefreelaapi.validate;

import io.github.alancavalcante_dev.codefreelaapi.exceptions.CpfExistsException;
import io.github.alancavalcante_dev.codefreelaapi.exceptions.EmailExistsException;
import io.github.alancavalcante_dev.codefreelaapi.model.Profile;
import io.github.alancavalcante_dev.codefreelaapi.repository.ProfileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class ProfileValidate {

    @Autowired
    private ProfileRepository repository;

    public void save(Profile profile) {

        Optional<Profile> profileOptionalEmail = repository.findByEmail(profile.getEmail());
        if (profileOptionalEmail.isPresent()) {
            throw new EmailExistsException("Já existe um perfil com esse Email no nosso sistema!");
        }

        Optional<Profile> profileOptionalCpf = repository.findByCpf(profile.getCpf());
        if (profileOptionalCpf.isPresent()) {
            throw new CpfExistsException("Já existe um perfil com esse CPF no nosso sistema!");
        }

    }

}
