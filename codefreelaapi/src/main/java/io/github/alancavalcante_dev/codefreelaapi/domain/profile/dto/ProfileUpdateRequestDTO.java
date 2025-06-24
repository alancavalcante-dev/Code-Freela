package io.github.alancavalcante_dev.codefreelaapi.domain.profile.dto;

import io.github.alancavalcante_dev.codefreelaapi.domain.entity.dto.AddressDTO;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProfileUpdateRequestDTO {

    @NotNull
    @NotBlank
    @Size(min = 2, message = "Nome: Mínimo de 2 caracteres e máximo de 100 caracteres", max = 100)
    String name;

    @NotNull
    @NotBlank
    @Email
    @Size(min = 2, message = "Email: Mínimo de 2 caracteres e máximo de 100 caracteres", max = 100)
    String email;

    @Size(min = 11, message = "Telefone: Mínimo de 11 caracteres e máximo de 13 caracteres", max = 13)
    String phone;

    @NotNull
    @NotBlank
    @Size(min = 11, message = "CPF: é de 11 caracteres, modelo: xxxxxxxxxxx", max = 11)
    String cpf;

    @NotNull(message = "Tem que definir se é desenvolvedor ou não.")
    Boolean isDeveloper;

    AddressDTO address;
}
