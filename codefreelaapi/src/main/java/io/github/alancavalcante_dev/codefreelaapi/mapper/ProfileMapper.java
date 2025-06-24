package io.github.alancavalcante_dev.codefreelaapi.mapper;

import io.github.alancavalcante_dev.codefreelaapi.domain.profile.dto.ProfileInsertRequestDTO;
import io.github.alancavalcante_dev.codefreelaapi.domain.profile.dto.ProfileResponseDTO;
import io.github.alancavalcante_dev.codefreelaapi.domain.profile.dto.ProfileUpdateRequestDTO;
import io.github.alancavalcante_dev.codefreelaapi.domain.profile.entity.Profile;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ProfileMapper {


    Profile toEntity(ProfileInsertRequestDTO client);

    Profile toEntityUpdate(ProfileUpdateRequestDTO client);

    ProfileInsertRequestDTO toRequestDTO(Profile client);

    ProfileResponseDTO toResponseDTO(Profile client);

}
