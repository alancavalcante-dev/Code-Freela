package io.github.alancavalcante_dev.codefreelaapi.domain.worker.commit.controller;

import io.github.alancavalcante_dev.codefreelaapi.domain.container.service.ContainerService;
import io.github.alancavalcante_dev.codefreelaapi.domain.entity.User;
import io.github.alancavalcante_dev.codefreelaapi.domain.worker.commit.service.WorkerCommit;
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

import java.util.List;
import java.util.UUID;

@Slf4j
@RestController
@RequestMapping("api/desenvolvedor/container")
@Tag(name="Área de Trabalho do Desenvolvedor")
public class CommitsDeveloperController {

    @Autowired
    private UserLogged logged;

    @Autowired
    private WorkerCommit workerCommit;

    @Autowired
    private ContainerService containerService;

    @GetMapping("{idContainer}/worker/commit")
    @PreAuthorize("hasRole('DEVELOPER')")
    public ResponseEntity<List<String>> getNameAlLCommits(@PathVariable UUID idContainer) {
        User user = logged.load();

        return containerService.getContainerById(user.getId(), idContainer)
                .map(container -> {
                    List<String> shaCommits = workerCommit.getNameCommits(user.getUsername(), container.getName());
                    return ResponseEntity.ok(shaCommits);
                })
                .orElseGet(() -> ResponseEntity.notFound().build());
    }



}
