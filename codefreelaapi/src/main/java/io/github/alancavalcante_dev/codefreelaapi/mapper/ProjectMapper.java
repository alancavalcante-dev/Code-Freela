package io.github.alancavalcante_dev.codefreelaapi.mapper;

import io.github.alancavalcante_dev.codefreelaapi.domain.entity.Project;
import io.github.alancavalcante_dev.codefreelaapi.presentation.dto.project.ProjectDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ProjectMapper {

    Project toEntity(ProjectDTO dto);
    ProjectDTO toDTO(Project entity);

}
