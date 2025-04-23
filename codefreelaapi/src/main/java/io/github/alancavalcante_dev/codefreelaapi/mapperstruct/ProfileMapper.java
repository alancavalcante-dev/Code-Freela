package io.github.alancavalcante_dev.codefreelaapi.mapperstruct;

import io.github.alancavalcante_dev.codefreelaapi.dto.profile.ProfileInsertRequestDTO;
import io.github.alancavalcante_dev.codefreelaapi.dto.profile.ProfileResponseDTO;
import io.github.alancavalcante_dev.codefreelaapi.dto.profile.ProfileUpdateRequestDTO;
import io.github.alancavalcante_dev.codefreelaapi.model.Profile;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ProfileMapper {


    Profile toEntity(ProfileInsertRequestDTO client);

    Profile toEntityUpdate(ProfileUpdateRequestDTO client);

    ProfileInsertRequestDTO toRequestDTO(Profile client);

    ProfileResponseDTO toResponseDTO(Profile client);

}
