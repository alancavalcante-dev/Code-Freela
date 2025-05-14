package io.github.alancavalcante_dev.codefreelaapi.domain.transaction;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@Slf4j
public class AuthorizationTransaction {

    private final RestTemplate restTemplate = new RestTemplate();
    private static final String URL = "https://run.mocky.io/v3/1c04f839-8153-4bfa-8b81-c32252f79b6a";

    public boolean authorized() {
        var response = restTemplate.getForObject(URL, AuthorizationResponse.class);
        if ( response.status().equals("success") && response.data().authorization()) {
            return true;
        } else return false;
    }
}