package io.github.alancavalcante_dev.codefreelaapi.controller;


import io.github.alancavalcante_dev.codefreelaapi.model.Client;
import io.github.alancavalcante_dev.codefreelaapi.service.ClientService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("clients")
@RequiredArgsConstructor
@Tag(name = "Cliente - OAuth2.0")
public class ClientController {

    private final ClientService service;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Cadastra um cliente")
    public void save(@RequestBody Client client) {
        service.save(client);
    }

}
