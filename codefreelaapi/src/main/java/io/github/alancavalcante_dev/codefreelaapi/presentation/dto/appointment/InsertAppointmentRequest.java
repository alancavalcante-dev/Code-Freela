package io.github.alancavalcante_dev.codefreelaapi.presentation.dto.appointment;

import java.time.LocalDateTime;

public record InsertAppointmentRequest(
        String comment,
        LocalDateTime dateStarting,
        LocalDateTime dateClosing
) {}