package io.github.alancavalcante_dev.codefreelaapi.mapperstruct;

import io.github.alancavalcante_dev.codefreelaapi.dto.user.UserRequestDTO;
import io.github.alancavalcante_dev.codefreelaapi.dto.user.UserResponseDTO;
import io.github.alancavalcante_dev.codefreelaapi.model.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {

    User responseToEntity(UserResponseDTO dto);
    User requestToEntity(UserRequestDTO dto);
    UserResponseDTO entityToResponse(User user);

}
