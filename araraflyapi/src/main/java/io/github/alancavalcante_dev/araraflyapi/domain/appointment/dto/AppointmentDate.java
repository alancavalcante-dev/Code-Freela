package io.github.alancavalcante_dev.araraflyapi.domain.appointment.dto;

import java.time.LocalDateTime;

public record AppointmentDate(
        LocalDateTime dateStarting,
        LocalDateTime dateClosing
) {
}
