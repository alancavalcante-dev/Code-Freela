package io.github.alancavalcante_dev.codefreelaapi.domain.container.dto;

import io.github.alancavalcante_dev.codefreelaapi.domain.appointment.dto.AppointmentDate;
import io.github.alancavalcante_dev.codefreelaapi.domain.appointment.entity.Appointment;
import io.github.alancavalcante_dev.codefreelaapi.domain.entity.enums.StateBusiness;
import io.github.alancavalcante_dev.codefreelaapi.domain.entity.enums.StateProject;
import io.github.alancavalcante_dev.codefreelaapi.domain.profile.dto.ProfileDataSimpleDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;


public record ContainerDetailsDTO(

        String id,

        String title,

        String description,

        StateBusiness stateBusiness,

        Map<String, BigDecimal> price,

        LocalDate dateClosingProject,

        LocalDate dateCreatedContainer,

        StateProject stateProject,

        List<AppointmentDate> appointments,

        ProfileDataSimpleDTO client,

        ProfileDataSimpleDTO developer
){ }
