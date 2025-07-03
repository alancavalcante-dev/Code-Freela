package io.github.alancavalcante_dev.araraflyapi.domain.appointment.dto;

import java.time.LocalDateTime;

public record InsertAppointmentRequest(
        String comment,
        LocalDateTime dateStarting,
        LocalDateTime dateClosing
) {}