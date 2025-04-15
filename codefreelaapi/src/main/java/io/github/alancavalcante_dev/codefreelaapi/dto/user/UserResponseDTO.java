package io.github.alancavalcante_dev.codefreelaapi.dto.user;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class UserResponseDTO {

    String idUsername;
    String username;
    List<String> roles;

}
