package io.github.alancavalcante_dev.codefreelaapi.dto.profile;

import io.github.alancavalcante_dev.codefreelaapi.dto.AddressDTO;
import io.github.alancavalcante_dev.codefreelaapi.dto.user.UserResponseDTO;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class ProfileResponseDTO {

    UUID idProfile;
    String name;
    String email;
    String phone;
    String cpf;
    boolean isPj;
    AddressDTO address;
    UserResponseDTO user;
}
