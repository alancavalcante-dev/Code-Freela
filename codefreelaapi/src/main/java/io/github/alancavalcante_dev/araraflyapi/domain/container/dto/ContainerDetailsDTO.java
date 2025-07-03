package io.github.alancavalcante_dev.araraflyapi.domain.container.dto;

import io.github.alancavalcante_dev.araraflyapi.domain.appointment.dto.AppointmentDate;
import io.github.alancavalcante_dev.araraflyapi.domain.entity.enums.StateBusiness;
import io.github.alancavalcante_dev.araraflyapi.domain.entity.enums.StateProject;
import io.github.alancavalcante_dev.araraflyapi.domain.profile.dto.ProfileDataSimpleDTO;

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

        String lastCommentDeveloper,

        ProfileDataSimpleDTO client,

        ProfileDataSimpleDTO developer

){ }
