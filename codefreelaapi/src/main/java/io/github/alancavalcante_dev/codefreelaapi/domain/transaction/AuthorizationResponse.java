package io.github.alancavalcante_dev.codefreelaapi.domain.transaction;

public record AuthorizationResponse(String status, Data data) {
    public record Data(Boolean authorization) {}
}
