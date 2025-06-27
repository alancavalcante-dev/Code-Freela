package io.github.alancavalcante_dev.codefreelaapi.domain.worker.deploy.controller;


import io.github.alancavalcante_dev.codefreelaapi.domain.container.entity.Container;
import io.github.alancavalcante_dev.codefreelaapi.domain.container.service.ContainerService;
import io.github.alancavalcante_dev.codefreelaapi.domain.worker.deploy.entity.Environment;
import io.github.alancavalcante_dev.codefreelaapi.domain.worker.deploy.service.EnvironmentService;
import io.github.alancavalcante_dev.codefreelaapi.infrastructure.security.UserLogged;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Slf4j
@RestController
@Tag(name="Worker - Environment do Deploy")
@RequestMapping("api/developer/container/") // idContainer/
@RequiredArgsConstructor
public class EnvironmentController {

    private final EnvironmentService environmentService;
    private final ContainerService containerService;
    private final UserLogged logged;

    @PreAuthorize("hasRole('DEVELOPER')")
    @PostMapping("{idContainer}/worker/deploy-service/{nameDeploy}/environment")
    public ResponseEntity<List<Environment>> getAllEnvironments(@PathVariable("idContainer") String id, @PathVariable String nameDeploy) {
        Optional<Container> container = containerService.getContainerById(logged.load().getId(), UUID.fromString(id));

        if (container.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        List<Environment> environments = environmentService.getBySurnameServiceAndContainer(container.get(), nameDeploy);
        if (environments.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(environments);
    }

    @PreAuthorize("hasRole('DEVELOPER')")
    @PutMapping("{idContainer}/worker/deploy-service/{nameDeploy}/environment/{idEnvironment}")
    public ResponseEntity<Environment> putEnvironment(
            @PathVariable("idContainer") String id,
            @PathVariable String nameDeploy,
            @PathVariable("idEnvironment") String idEnvironment,
            @RequestBody Environment variable)
    {
        Optional<Container> container = containerService.getContainerById(logged.load().getId(), UUID.fromString(id));

        if (container.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        List<Environment> environments = environmentService.getBySurnameServiceAndContainer(container.get(), nameDeploy);
        if (environments.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        Environment environment = (Environment) environments.stream().filter(e -> e.getIdEnvironment().equals(idEnvironment));
        environment.setVariable(variable.getVariable());
        Environment environmentSave = environmentService.save(environment);
        return ResponseEntity.ok(environmentSave);
    }


    @PreAuthorize("hasRole('DEVELOPER')")
    @DeleteMapping("{idContainer}/worker/deploy-service/{nameDeploy}/environment/{idEnvironment}")
    public ResponseEntity<Environment> deleteEnvironment(
            @PathVariable("idContainer") String id,
            @PathVariable String nameDeploy,
            @PathVariable("idEnvironment") String idEnvironment)
    {
        Optional<Container> container = containerService.getContainerById(logged.load().getId(), UUID.fromString(id));

        if (container.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        List<Environment> environments = environmentService.getBySurnameServiceAndContainer(container.get(), nameDeploy);
        if (environments.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        Environment environment = (Environment) environments.stream().filter(e -> e.getIdEnvironment().equals(idEnvironment));
        environmentService.delete(environment);
        return ResponseEntity.ok().build();
    }


}
