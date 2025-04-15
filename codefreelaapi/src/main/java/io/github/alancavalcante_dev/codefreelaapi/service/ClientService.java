package io.github.alancavalcante_dev.codefreelaapi.service;

import io.github.alancavalcante_dev.codefreelaapi.model.Client;
import io.github.alancavalcante_dev.codefreelaapi.repository.ClientRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ClientService {

    private final ClientRepository repository;
    private final PasswordEncoder encoder;

    public Client save(Client client) {
        var passwordEncrypy = encoder.encode(client.getClientSecret());
        client.setClientSecret(passwordEncrypy);
        return repository.save(client);
    }

    public Client getClientById(String clientId) {
        return repository.findByClientId(clientId);
    }

}
