package io.github.alancavalcante_dev.araraflyapi.domain.portfolio.dto;

import io.github.alancavalcante_dev.araraflyapi.domain.worker.deploy.entity.Environment;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class PortfolioDeveloperDTO {

    String idDeveloper;

    @NotNull
    @NotBlank
    @Size(min = 2, message = "Nome: Mínimo de 2 caracteres e máximo de 100 caracteres", max = 100)
    String name;

    @NotNull
    @NotBlank
    @Email
    @Size(min = 2, message = "Email: Mínimo de 2 caracteres e máximo de 100 caracteres", max = 100)
    String email;

    @NotBlank
    @NotNull
    @Size(message = "Mínimo de 10 caracteres e máximo de 100 caracteres", min = 10, max = 100)
    String presentation;

    @NotBlank
    @NotNull
    @Size(message = "Mínimo de 10 caracteres e máximo de 100 caracteres", min = 10, max = 180)
    String resume;

    @NotBlank
    @NotNull
    List<Environment> environments;

}
