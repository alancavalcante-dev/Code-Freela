package io.github.alancavalcante_dev.codefreelaapi.presentation.controller;

import java.time.LocalDateTime;

public record WorkSessionRequest(
        String comment,
        LocalDateTime dateStarting,
        LocalDateTime dateClosing
) {}