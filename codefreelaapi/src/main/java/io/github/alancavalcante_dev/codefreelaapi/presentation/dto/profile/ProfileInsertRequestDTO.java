package io.github.alancavalcante_dev.codefreelaapi.presentation.dto.profile;


import io.github.alancavalcante_dev.codefreelaapi.presentation.dto.AddressDTO;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProfileInsertRequestDTO {

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

    @NotNull(message = "Tem que definir se é Pessoa Juridica ou não.")
    boolean isPj;

    AddressDTO address;

}
