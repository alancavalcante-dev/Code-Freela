package io.github.alancavalcante_dev.codefreelaapi.domain.worker.deploy.controller;


import io.github.alancavalcante_dev.codefreelaapi.domain.worker.deploy.entity.Deploy;
import io.github.alancavalcante_dev.codefreelaapi.domain.worker.deploy.service.DeployService;
import io.github.alancavalcante_dev.codefreelaapi.infrastructure.security.UserLogged;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@Tag(name="Worker - Serviço de Deploy")
@RequestMapping("api/developer/container/") // idContainer/
public class DeployServiceController {

    @Autowired
    private DeployService deployService;

    @Autowired
    private UserLogged logged;

    @PreAuthorize("hasRole('DEVELOPER')")
    @GetMapping("{idContainer}/worker/deploy/service")
    public ResponseEntity<Deploy> getDataDeployService(@PathVariable("idContainer") String id) {
        return deployService.getDeploy(logged.load(), id)
                .map(ResponseEntity::ok)
                .orElseGet( () -> ResponseEntity.notFound().build() );
    }

}
