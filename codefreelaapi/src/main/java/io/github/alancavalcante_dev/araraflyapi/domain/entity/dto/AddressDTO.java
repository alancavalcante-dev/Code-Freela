package io.github.alancavalcante_dev.araraflyapi.domain.entity.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AddressDTO {

    @NotNull
    @Size(min = 2, message = "Estado: Mínimo de 2 caracteres e máximo de 50 caracteres", max = 50)
    String state;

    @NotNull
    @Size(min = 2, message = "Cidade: Mínimo de 2 caracteres e máximo de 50 caracteres", max = 50)
    String city;

    @NotNull
    @Size(min = 2, message = "Bairro: Mínimo de 2 caracteres e máximo de 50 caracteres", max = 50)
    String neighborhood;

    String address;

    @NotNull
    int addressNumber;

}
