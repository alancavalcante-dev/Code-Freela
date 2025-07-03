package io.github.alancavalcante_dev.araraflyapi.domain.worker.deploy.service;


import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

@Service
public class DockerCommandExecutor {

    /**
     * Executa um comando do sistema operacional e captura sua saída em tempo real.
     * @param workingDirectory O diretório onde o comando será executado.
     * @param command A lista de strings representando o comando e seus argumentos.
     * @throws IOException Se houver um erro ao iniciar o processo.
     * @throws InterruptedException Se a thread for interrompida.
     * @throws TimeoutException Se o comando exceder o tempo limite.
     */
    public void executeCommand(String workingDirectory, List<String> command)
            throws IOException, InterruptedException, TimeoutException {

        System.out.printf("Executando comando em '%s': %s%n", workingDirectory, String.join(" ", command));

        ProcessBuilder processBuilder = new ProcessBuilder(command);
        processBuilder.directory(new File(workingDirectory));
        processBuilder.redirectErrorStream(true); // Junta a saída de erro e a padrão

        Process process = processBuilder.start();

        // Bloco para garantir que o leitor será fechado
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()))) {
            String line;
            // Lê a saída linha por linha enquanto o processo estiver vivo
            while ((line = reader.readLine()) != null) {
                // Envia a linha para um sistema de log ou para o console
                System.out.println("LOG > " + line);
            }
        }

        // Espera pelo fim do processo com um timeout de 5 minutos
        if (!process.waitFor(5, TimeUnit.MINUTES)) {
            process.destroyForcibly();
            throw new TimeoutException("Comando demorou mais de 5 minutos para executar e foi encerrado.");
        }

        // Verifica o código de saída após o término
        int exitCode = process.exitValue();
        if (exitCode != 0) {
            throw new RuntimeException("Comando falhou com código de saída: " + exitCode);
        }

        System.out.println("Comando executado com sucesso!");
    }
}