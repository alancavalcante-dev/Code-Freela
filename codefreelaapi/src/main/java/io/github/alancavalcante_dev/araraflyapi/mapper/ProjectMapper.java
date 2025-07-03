package io.github.alancavalcante_dev.araraflyapi.mapper;

import io.github.alancavalcante_dev.araraflyapi.domain.project.entity.Project;
import io.github.alancavalcante_dev.araraflyapi.domain.project.dto.ProjectDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ProjectMapper {

    @Mapping(source = "idProject", target = "idProject", ignore = true)
    Project toEntity(ProjectDTO dto);

    ProjectDTO toDTO(Project entity);

}
