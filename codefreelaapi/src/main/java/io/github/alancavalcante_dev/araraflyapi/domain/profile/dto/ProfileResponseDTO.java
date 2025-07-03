package io.github.alancavalcante_dev.araraflyapi.domain.profile.dto;

import io.github.alancavalcante_dev.araraflyapi.domain.entity.dto.AddressDTO;
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
