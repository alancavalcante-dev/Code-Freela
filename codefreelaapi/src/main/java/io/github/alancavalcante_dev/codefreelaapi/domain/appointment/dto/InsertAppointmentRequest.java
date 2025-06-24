package io.github.alancavalcante_dev.codefreelaapi.domain.appointment.dto;

import java.time.LocalDateTime;

public record InsertAppointmentRequest(
        String comment,
        LocalDateTime dateStarting,
        LocalDateTime dateClosing
) {}