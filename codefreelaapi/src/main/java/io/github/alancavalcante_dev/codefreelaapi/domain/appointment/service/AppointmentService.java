package io.github.alancavalcante_dev.codefreelaapi.domain.appointment.service;

import io.github.alancavalcante_dev.codefreelaapi.domain.appointment.entity.Appointment;
import io.github.alancavalcante_dev.codefreelaapi.infrastructure.repository.WorkSessionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AppointmentService {

    private final WorkSessionRepository repository;

    public Appointment saveWorkSession(Appointment appointment) {
        return repository.save(appointment);
    }


}
