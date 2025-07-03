package io.github.alancavalcante_dev.araraflyapi.mapper;

import io.github.alancavalcante_dev.araraflyapi.domain.profile.dto.ProfileResponseDTO;
import io.github.alancavalcante_dev.araraflyapi.domain.profile.dto.ProfileUpdateRequestDTO;
import io.github.alancavalcante_dev.araraflyapi.domain.profile.entity.Profile;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ProfileMapper {

    Profile toEntityUpdate(ProfileUpdateRequestDTO client);

    ProfileResponseDTO toResponseDTO(Profile client);

}
