package io.github.alancavalcante_dev.codefreelaapi.domain.worker.deploy.service;

import io.github.alancavalcante_dev.codefreelaapi.domain.container.entity.Container;
import io.github.alancavalcante_dev.codefreelaapi.domain.worker.deploy.entity.Deploy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.*;

import org.apache.tomcat.util.http.fileupload.FileUtils; // Exemplo para limpeza
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
public class Deployment {

    @Value("${GITEA_TOKEN}")
    private String giteaToken;

    @Value("${HOST_IA}")
    private String giteaHost;

    @Autowired
    private DockerfileGenerator dockerfileGenerator;

    @Autowired
    private DockerCommandExecutor commandExecutor;

    public Map<String, String> process(Deploy deploy) {
        Container container = deploy.getContainer();
        String repoOwner = container.getProjectBusiness().getUserDeveloper().getUsername();
        String repoName = container.getName();

        String uniqueTag = UUID.randomUUID().toString().substring(0, 8);
        String imageName = String.format("deploy-%s-%s:%s", repoOwner, repoName, uniqueTag).toLowerCase();
        String containerName = imageName; deploy.getSurnameService();

        // 1. Cria um diretório de trabalho temporário e único
        Path workspace;
        try {
            workspace = Files.createDirectories(Paths.get("/tmp/deployments/" + uniqueTag));
            System.out.println("Workspace criado em: " + workspace);
        } catch (IOException e) {
            throw new RuntimeException("Falha ao criar diretório de trabalho.", e);
        }

        try {
            // 2. Gera o conteúdo do Dockerfile em memória
            String dockerfileContent = dockerfileGenerator.generateDockerfile(
                    deploy, giteaHost, repoOwner, repoName
            );

            // 3. Salva o Dockerfile gerado na raiz do workspace
            Files.writeString(workspace.resolve("Dockerfile"), dockerfileContent);
            System.out.println("Dockerfile salvo no workspace.");

            // 4. Executa o 'docker build'
            List<String> buildCommand = List.of(
                    "docker", "build",
                    "--build-arg", "GITEA_TOKEN=" + giteaToken,
                    "-t", imageName, // Define a tag da imagem
                    "."
            );
            commandExecutor.executeCommand(workspace.toString(), buildCommand);

            // 5. Executa o 'docker run' para iniciar o container
            List<String> runCommand = new ArrayList<>(List.of("docker", "run", "-d", "--rm", "--name", containerName)); // -d (background), --rm (remove ao parar)

            // Adiciona mapeamento de portas
            deploy.getPortsExposes().forEach(p -> {
                runCommand.add("-p");
                runCommand.add(String.format("%s:%s", p.getPort(), p.getPort()));
            });

            // Adiciona variáveis de ambiente
            deploy.getVariableEnvironments().forEach(env -> {
                runCommand.add("-e");
                runCommand.add(String.format("%s=%s", env.getKey(), env.getValue()));
            });

            runCommand.add(imageName); // O nome da imagem a ser executada

            // Executa o docker run. O diretório de trabalho não importa para este comando.
            commandExecutor.executeCommand(System.getProperty("user.home"), runCommand);

        } catch (IOException | InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new RuntimeException("Falha durante o processo de deploy: " + e.getMessage(), e);
        } finally {
            // (Opcional mas MUITO recomendado) Limpa o workspace
            try {
                // A biblioteca Apache Commons IO tem um bom utilitário para isso
                FileUtils.deleteDirectory(workspace.toFile());
                System.out.println("Workspace limpo com sucesso: " + workspace);
            } catch (IOException e) {
                System.err.println("Aviso: Falha ao limpar o diretório de trabalho: " + workspace);
            }
        }

        return Map.of(
                "status", "Deploy concluído com sucesso",
                "imageName", imageName,
                "containerName", containerName
        );
    }


    /**
     * Para e remove um container Docker existente.
     * @param deploy Objeto que deve conter a identificação do container a ser parado.
     * @return Um mapa com o status da operação.
     */
    public Map<String, String> stop(Deploy deploy) {
        String containerName = deploy.getSurnameService();

        if (containerName == null || containerName.isBlank()) {
            throw new IllegalArgumentException("O nome do container não foi fornecido para a operação de stop.");
        }

        System.out.println("Iniciando processo para parar o container: " + containerName);

        List<String> stopCommand = List.of("docker", "stop", containerName);
        List<String> removeCommand = List.of("docker", "rm", containerName);

        try {
            String defaultWorkingDirectory = System.getProperty("user.home");

            commandExecutor.executeCommand(defaultWorkingDirectory, stopCommand);
            System.out.println("Container parado com sucesso: " + containerName);

            commandExecutor.executeCommand(defaultWorkingDirectory, removeCommand);
            System.out.println("Container removido com sucesso: " + containerName);

        } catch (IOException | InterruptedException e) {
            System.err.println("Erro ao parar/remover o container '" + containerName + "'. Ele já pode ter sido parado/removido. Erro: " + e.getMessage());
            Thread.currentThread().interrupt();
            return Map.of(
                    "status", "Erro",
                    "message", "Falha ao parar ou remover o container: " + e.getMessage()
            );
        }
        return Map.of(
                "status", "Sucesso",
                "message", "Container " + containerName + " foi parado e removido."
        );
    }
}