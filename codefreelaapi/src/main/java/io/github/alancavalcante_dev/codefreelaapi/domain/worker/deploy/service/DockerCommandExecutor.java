package io.github.alancavalcante_dev.codefreelaapi.domain.worker.deploy.service;


import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Service
public class DockerCommandExecutor {
    public void executeCommand(String workingDirectory, List<String> command) throws IOException, InterruptedException {
        // ... implementação completa com ProcessBuilder ...
    }
}