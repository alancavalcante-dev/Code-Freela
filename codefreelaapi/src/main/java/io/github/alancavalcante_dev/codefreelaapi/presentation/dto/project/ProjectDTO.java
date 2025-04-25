package io.github.alancavalcante_dev.codefreelaapi.presentation.dto.project;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
public class ProjectDTO {

    @NotBlank
    @NotNull
    @Size(min = 5, max = 100, message = "...")
    String title;

    @NotBlank
    @NotNull
    @Size(min = 5, max = 100, message = "...")
    String description;

    BigDecimal priceDay;

    BigDecimal priceHour;

    BigDecimal priceProject;

    @NotBlank
    @NotNull
    LocalDate closingDate;

}
