package io.github.alancavalcante_dev.codefreelaapi.domain.worker.deploy.service;

import io.github.alancavalcante_dev.codefreelaapi.domain.container.entity.Container;
import io.github.alancavalcante_dev.codefreelaapi.domain.worker.deploy.entity.Deploy;
import io.github.alancavalcante_dev.codefreelaapi.domain.worker.deploy.entity.TypeService;
import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.util.http.fileupload.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeoutException;

@Slf4j
@Service
public class Deployment {

    @Value("${gitea.token}")
    private String giteaToken;

    @Autowired
    private DeployService deployPersistenceService;

    @Autowired
    private DockerfileGenerator dockerfileGenerator;

    @Autowired
    private DockerCommandExecutor commandExecutor;

    @Autowired
    private TaskScheduler taskScheduler;

    public Map<String, String> process(Deploy deploy) {
        Container container = deploy.getContainer();
        String repoOwner = container.getProjectBusiness().getUserDeveloper().getUsername();
        String repoName = container.getName();

        String containerName = deploy.getSurnameService();

        String imageName = String.format("image-for-%s", containerName).toLowerCase();

        String uniqueTag = UUID.randomUUID().toString().substring(0, 8);
        Path workspace;
        try {
            String tempDir = System.getProperty("java.io.tmpdir");
            workspace = Files.createDirectories(Paths.get(tempDir, "deployments", uniqueTag));
            log.info("Workspace criado em: {}", workspace);
        } catch (IOException e) {
            throw new RuntimeException("Falha ao criar diretório de trabalho.", e);
        }

        try {
            String dockerfileContent = dockerfileGenerator.generateDockerfile(deploy, "host.docker.internal:3000", repoOwner, repoName);
            Files.writeString(workspace.resolve("Dockerfile"), dockerfileContent);
            log.info("Dockerfile salvo no workspace.");

            List<String> buildCommand = List.of(
                    "docker", "build",
                    "--build-arg", "GITEA_TOKEN=" + giteaToken,
                    "--build-arg", "CACHE_BUSTER=" + UUID.randomUUID().toString(),
                    "-t", imageName,
                    "."
            );
            commandExecutor.executeCommand(workspace.toString(), buildCommand);

            List<String> runCommand = new ArrayList<>(List.of("docker", "run", "-d", "--rm", "--name", containerName));

            deploy.getPortsExposes().forEach(p -> {
                String externalPort = p.getPort();
                String internalPort = externalPort;
                if (deploy.getTypeService() == TypeService.STATIC) {
                    internalPort = "80";
                }
                runCommand.add("-p");
                runCommand.add(String.format("%s:%s", externalPort, internalPort));
            });

            deploy.getVariableEnvironments().forEach(env -> {
                runCommand.add("-e");
                runCommand.add(String.format("%s=%s", env.getKey(), env.getValue()));
            });

            runCommand.add(imageName);
            commandExecutor.executeCommand(System.getProperty("user.home"), runCommand);

            log.info("Container {} iniciado. Agendando parada para daqui a 10 minutos.", containerName);

            long delayInMillis = 10 * 60 * 200;
            Instant stopTime = Instant.now().plusMillis(delayInMillis);

            deploy.setIsUp(true);
            deployPersistenceService.save(deploy);

            taskScheduler.schedule(() -> stop(deploy), stopTime);

        } catch (IOException | InterruptedException | TimeoutException e) {
            Thread.currentThread().interrupt();
            throw new RuntimeException("Falha durante o processo de deploy: " + e.getMessage(), e);
        } finally {
            try {
                FileUtils.deleteDirectory(workspace.toFile());
                log.info("Workspace limpo com sucesso: {}", workspace);
            } catch (IOException e) {
                log.info("Aviso: Falha ao limpar o diretório de trabalho: {}", workspace);
            }
        }

        return Map.of(
                "status", "success",
                "imageName", imageName,
                "containerName", containerName
        );
    }


    /**
     * Para e remove um container Docker e sua imagem associada usando nomes previsíveis.
     * @param deploy Objeto que contém os dados do deploy, incluindo o surnameService.
     * @return Um mapa com o status da operação.
     */
    public Map<String, String> stop(Deploy deploy) {
        String containerName = deploy.getSurnameService();
        String imageName = String.format("image-for-%s", containerName).toLowerCase();

        if (containerName == null || containerName.isBlank()) {
            return Map.of("status", "no_action", "message", "Nome do serviço (container) não fornecido.");
        }

        log.info("Iniciando processo para parar container '{}' e remover imagem '{}'", containerName, imageName);

        try {
            String defaultWorkingDirectory = System.getProperty("user.home");

            List<String> forceRemoveContainerCommand = List.of("docker", "rm", "-f", containerName);
            commandExecutor.executeCommand(defaultWorkingDirectory, forceRemoveContainerCommand);
            log.info("Comando para remover container executado.");

            List<String> removeImageCommand = List.of("docker", "rmi", "-f", imageName);
            commandExecutor.executeCommand(defaultWorkingDirectory, removeImageCommand);
            log.info("Comando para remover imagem executado.");

        } catch (Exception e) {
            log.info("Ocorreu um erro durante a limpeza do ambiente Docker (pode ser normal se o recurso já foi removido): {}", e.getMessage());
        } finally {
            deploy.setIsUp(false);
            deployPersistenceService.save(deploy);
            log.info("Estado do deploy '{}' atualizado para 'desligado'.", deploy.getSurnameService());
        }

        return Map.of("status", "success", "message", "Comandos de limpeza para o ambiente " + containerName + " foram executados.");
    }
}