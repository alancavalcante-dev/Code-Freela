package io.github.alancavalcante_dev.codefreelaapi.domain.appointment.dto;

import java.time.LocalDateTime;

public record AppointmentDate(
        LocalDateTime dateStarting,
        LocalDateTime dateClosing
) {
}
