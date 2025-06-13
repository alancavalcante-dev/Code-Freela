package io.github.alancavalcante_dev.codefreelaapi.presentation.dto.appointment;

import java.time.LocalDateTime;
import java.util.UUID;

public record AppointmentsRequests(
        UUID idAppointment,
        LocalDateTime dateCreated
) {}
