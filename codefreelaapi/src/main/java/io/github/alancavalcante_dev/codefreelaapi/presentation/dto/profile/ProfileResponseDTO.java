package io.github.alancavalcante_dev.codefreelaapi.presentation.dto.profile;

import io.github.alancavalcante_dev.codefreelaapi.presentation.dto.AddressDTO;
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
    Boolean isDeveloper;
    AddressDTO address;
}
