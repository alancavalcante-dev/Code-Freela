package io.github.alancavalcante_dev.araraflyapi.domain.appointment.service;

import io.github.alancavalcante_dev.araraflyapi.domain.appointment.entity.Appointment;
import io.github.alancavalcante_dev.araraflyapi.infrastructure.repository.WorkSessionRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;


@Slf4j
@Service
@RequiredArgsConstructor
public class AppointmentService {


    private final WorkSessionRepository repository;


    public Appointment saveWorkSession(Appointment appointment) {
        return repository.save(appointment);
    }




}
