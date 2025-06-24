package io.github.alancavalcante_dev.codefreelaapi.domain.appointment.dto;

import java.time.LocalDateTime;
import java.util.UUID;

public record AppointmentsRequests(
        UUID idAppointment,
        LocalDateTime dateCreated
) {}
