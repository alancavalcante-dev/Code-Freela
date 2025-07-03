package io.github.alancavalcante_dev.araraflyapi.domain.worker.deploy.controller;


import io.github.alancavalcante_dev.araraflyapi.domain.container.entity.Container;
import io.github.alancavalcante_dev.araraflyapi.domain.container.service.ContainerService;
import io.github.alancavalcante_dev.araraflyapi.domain.worker.deploy.entity.Deploy;
import io.github.alancavalcante_dev.araraflyapi.domain.worker.deploy.service.DeployService;
import io.github.alancavalcante_dev.araraflyapi.domain.worker.deploy.service.Deployment;
import io.github.alancavalcante_dev.araraflyapi.infrastructure.security.UserLogged;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Optional;
import java.util.UUID;


@Slf4j
@RestController
@Tag(name="Worker - Deployment - Subir projeto")
@RequestMapping("api/developer/container/")
@RequiredArgsConstructor
public class DeploymentController {


    private final ContainerService containerService;
    private final DeployService deployService;
    private final Deployment deployment;
    private final UserLogged logged;


    @PreAuthorize("hasRole('DEVELOPER')")
    @PostMapping("{idContainer}/worker/deploy-service/start")
    public ResponseEntity<Map<String, String>> startContainer(@PathVariable("idContainer") String id) {
        Optional<Container> container = containerService.getContainerById(logged.load().getId(), UUID.fromString(id));
        if (container.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        Optional<Deploy> deploy = deployService.getDeploy(container.get());
        if (deploy.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        Map<String, String> process = deployment.process(deploy.get());
        return ResponseEntity.ok(process);
    }


    @PreAuthorize("hasRole('DEVELOPER')")
    @PostMapping("{idContainer}/worker/deploy-service/stop")
    public ResponseEntity<Map<String, String>> stopContainer(@PathVariable("idContainer") String id) {
        Optional<Container> container = containerService.getContainerById(logged.load().getId(), UUID.fromString(id));
        if (container.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        Optional<Deploy> deploy = deployService.getDeploy(container.get());
        if (deploy.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        Map<String, String> process = deployment.stop(deploy.get());
        if (process.get("status").equals("error")) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(process);
    }

}
