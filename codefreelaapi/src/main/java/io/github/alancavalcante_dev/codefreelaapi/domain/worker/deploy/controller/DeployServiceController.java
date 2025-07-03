package io.github.alancavalcante_dev.codefreelaapi.domain.worker.deploy.controller;


import io.github.alancavalcante_dev.codefreelaapi.domain.container.entity.Container;
import io.github.alancavalcante_dev.codefreelaapi.domain.container.service.ContainerService;
import io.github.alancavalcante_dev.codefreelaapi.domain.worker.deploy.entity.Deploy;
import io.github.alancavalcante_dev.codefreelaapi.domain.worker.deploy.entity.Environment;
import io.github.alancavalcante_dev.codefreelaapi.domain.worker.deploy.service.DeployService;
import io.github.alancavalcante_dev.codefreelaapi.infrastructure.security.UserLogged;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Slf4j
@RestController
@Tag(name="Worker - Serviço de Deploy")
@RequestMapping("api/developer/container/") // idContainer/
@RequiredArgsConstructor
public class DeployServiceController {

    private final ContainerService containerService;
    private final DeployService deployService;
    private final UserLogged logged;


    @PreAuthorize("hasRole('DEVELOPER')")
    @GetMapping("{idContainer}/worker/deploy-service")
    public ResponseEntity<Deploy> getDataDeployService(@PathVariable("idContainer") String id) {
        Optional<Container> container = containerService.getContainerById(logged.load().getId(), UUID.fromString(id));
        if (container.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        Optional<Deploy> deploy = deployService.getDeploy(container.get());
        return deploy.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.noContent().build());
    }


    @Transactional
    @PreAuthorize("hasRole('DEVELOPER')")
    @PostMapping("{idContainer}/worker/deploy-service")
    public ResponseEntity<Deploy> registerDataDeployService(@PathVariable("idContainer") String id, @RequestBody Deploy data) {
        Optional<Container> container = containerService.getContainerById(logged.load().getId(), UUID.fromString(id));

        if (container.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        data.setContainer(container.get());
        data.setIsUp(false);
        Deploy deploy = deployService.save(data);
        return ResponseEntity.ok(deploy);
    }

    @Transactional
    @PreAuthorize("hasRole('DEVELOPER')")
    @PutMapping("{idContainer}/worker/deploy-service")
    public ResponseEntity<Deploy> updateDataDeployService(@PathVariable("idContainer") String id, @RequestBody Deploy data) {
        Optional<Container> container = containerService.getContainerById(logged.load().getId(), UUID.fromString(id));

        if (container.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        Optional<Deploy> deployOpt = deployService.getDeploy(container.get());
        if (deployOpt.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        Deploy deploy = deployOpt.get();

        deploy.setSurnameService(data.getSurnameService());
        deploy.setTypeService(data.getTypeService());
        deploy.setLanguage(data.getLanguage());
        deploy.setEntrypoint(data.getEntrypoint());

        Deploy deploySaved = deployService.save(deploy);

        return ResponseEntity.ok(deploySaved);
    }


}
                                                                       