package io.github.alancavalcante_dev.codefreelaapi.domain.worker.deploy.controller;

import io.github.alancavalcante_dev.codefreelaapi.domain.container.entity.Container;
import io.github.alancavalcante_dev.codefreelaapi.domain.container.service.ContainerService;
import io.github.alancavalcante_dev.codefreelaapi.domain.worker.deploy.entity.Deploy;
import io.github.alancavalcante_dev.codefreelaapi.domain.worker.deploy.entity.Environment;
import io.github.alancavalcante_dev.codefreelaapi.domain.worker.deploy.entity.PortExpose;
import io.github.alancavalcante_dev.codefreelaapi.domain.worker.deploy.service.DeployService;
import io.github.alancavalcante_dev.codefreelaapi.domain.worker.deploy.service.PortExposeService;
import io.github.alancavalcante_dev.codefreelaapi.infrastructure.security.UserLogged;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;



@Slf4j
@RestController
@Tag(name="Worker - PortExpose do Deploy")
@RequestMapping("api/developer/container/") // idContainer/
@RequiredArgsConstructor
public class PortExposeController {

    private final PortExposeService portExposeService;
    private final ContainerService containerService;
    private final DeployService deployService;
    private final UserLogged logged;

    @PreAuthorize("hasRole('DEVELOPER')")
    @GetMapping("{idContainer}/worker/deploy-service/{nameDeploy}/port-expose")
    public ResponseEntity<List<PortExpose>> getAllPorts(@PathVariable("idContainer") String id, @PathVariable String nameDeploy) {
        Optional<Container> container = containerService.getContainerById(logged.load().getId(), UUID.fromString(id));

        if (container.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        List<PortExpose> portExposes = portExposeService.getBySurnameServiceAndContainer(container.get(), nameDeploy);
        if (portExposes.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(portExposes);
    }

    @PreAuthorize("hasRole('DEVELOPER')")
    @PostMapping("{idContainer}/worker/deploy-service/{nameDeploy}/port-expose")
    public ResponseEntity<PortExpose> postAllPorts(
            @PathVariable("idContainer") String id,
            @PathVariable String nameDeploy,
            @RequestBody PortExpose data
    ) {
        Optional<Container> container = containerService.getContainerById(logged.load().getId(), UUID.fromString(id));

        if (container.isEmpty()) {
            log.info("Container não encontrada");
            return ResponseEntity.notFound().build();
        }

        List<PortExpose> portExposes = portExposeService.getBySurnameServiceAndContainer(container.get(), nameDeploy);
        if (portExposes.isEmpty()) {
            log.info("Service/Porta não encontrada");
            return ResponseEntity.noContent().build();
        }

        Optional<Deploy> deploy = deployService.getDeploy(container.get());
        if (deploy.isEmpty()) {
            log.info("Deploy não encontrada");
            return ResponseEntity.notFound().build();
        }

        data.setDeploy(deploy.get());
        PortExpose saved = portExposeService.save(data);
        return ResponseEntity.ok(saved);
    }

    @PreAuthorize("hasRole('DEVELOPER')")
    @PutMapping("{idContainer}/worker/deploy-service/{nameDeploy}/port-expose/{idPort}")
    public ResponseEntity<PortExpose> putPort(
            @PathVariable("idContainer") String id,
            @PathVariable String nameDeploy,
            @PathVariable("idEnvironment") String idPort,
            @RequestBody PortExpose port)
    {
        Optional<Container> container = containerService.getContainerById(logged.load().getId(), UUID.fromString(id));

        if (container.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        List<PortExpose> portExposes = portExposeService.getBySurnameServiceAndContainer(container.get(), nameDeploy);
        if (portExposes.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        PortExpose portExpose = (PortExpose) portExposes.stream().filter(e -> e.getIdPortExpose().equals(idPort));
        portExpose.setPort(port.getPort());
        PortExpose portSave = portExposeService.save(portExpose);
        return ResponseEntity.ok(portSave);
    }


    @PreAuthorize("hasRole('DEVELOPER')")
    @DeleteMapping("{idContainer}/worker/deploy-service/{nameDeploy}/port-expose/{idPort}")
    public ResponseEntity<Environment> deletePort(
            @PathVariable("idContainer") String id,
            @PathVariable String nameDeploy,
            @PathVariable("idEnvironment") String idPort)
    {
        Optional<Container> container = containerService.getContainerById(logged.load().getId(), UUID.fromString(id));

        if (container.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        List<PortExpose> portExposes = portExposeService.getBySurnameServiceAndContainer(container.get(), nameDeploy);
        if (portExposes.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        PortExpose portExpose = (PortExpose) portExposes.stream().filter(e -> e.getIdPortExpose().equals(idPort));
        portExposeService.delete(portExpose);
        return ResponseEntity.ok().build();
    }


}