package io.github.alancavalcante_dev.codefreelaapi.mapper;

import io.github.alancavalcante_dev.codefreelaapi.presentation.dto.profile.ProfileInsertRequestDTO;
import io.github.alancavalcante_dev.codefreelaapi.presentation.dto.profile.ProfileResponseDTO;
import io.github.alancavalcante_dev.codefreelaapi.presentation.dto.profile.ProfileUpdateRequestDTO;
import io.github.alancavalcante_dev.codefreelaapi.domain.entity.Profile;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ProfileMapper {


    Profile toEntity(ProfileInsertRequestDTO client);

    Profile toEntityUpdate(ProfileUpdateRequestDTO client);

    ProfileInsertRequestDTO toRequestDTO(Profile client);

    ProfileResponseDTO toResponseDTO(Profile client);

}
