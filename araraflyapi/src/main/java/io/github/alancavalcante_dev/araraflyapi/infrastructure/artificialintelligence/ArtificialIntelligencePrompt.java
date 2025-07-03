package io.github.alancavalcante_dev.araraflyapi.infrastructure.artificialintelligence;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.concurrent.CompletableFuture;


@Service
public class ArtificialIntelligencePrompt {

    private final WebClient webClient;

    public ArtificialIntelligencePrompt(@Value("${HOST_IA}") String hostIa) {
        this.webClient = WebClient.builder()
                .baseUrl(hostIa)
                .build();
    }

    @Async
    public CompletableFuture<String> reviewCod(String code, String comment, LocalDateTime starting, LocalDateTime closing) {
        Map<String, Object> payload = Map.of(
                "model", "deepseek-coder",
                "prompt", """
                        Analise o código e descreva o que foi feito explicando de forma clara para o cliente.
                        O código de hoje foi iniciado às %s e terminado às %s. 
                        O cliente deixou o seguinte comentário: %s
                        Agora, com todas essas informações, analise os patches dos commits e explique para o cliente o que foi feito:
                        
                        ```
                        %s
                        ```
                        """.formatted(starting, closing, comment, code),
                "stream", false
        );

        return CompletableFuture.completedFuture(webClient.post()
                .uri("/api/generate")
                .bodyValue(payload)
                .retrieve()
                .bodyToMono(Map.class)
                .map(resp -> (String) resp.get("response"))
                .block());
    }
}
