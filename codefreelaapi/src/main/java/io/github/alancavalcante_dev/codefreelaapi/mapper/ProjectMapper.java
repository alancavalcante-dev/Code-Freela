package io.github.alancavalcante_dev.codefreelaapi.mapper;

import io.github.alancavalcante_dev.codefreelaapi.domain.project.entity.Project;
import io.github.alancavalcante_dev.codefreelaapi.domain.project.dto.ProjectDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ProjectMapper {

    @Mapping(source = "idProject", target = "idProject", ignore = true)
    Project toEntity(ProjectDTO dto);

    ProjectDTO toDTO(Project entity);

}
