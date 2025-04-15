package io.github.alancavalcante_dev.codefreelaapi.dto.businesproject;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
public class BusinessProjectUpdateDTO {
    @NotNull
    @NotBlank
    @Size(min=2, message = "Erro", max = 100)
    String title;

    @NotNull
    @NotBlank
    @Size(min=2, message = "Erro", max = 100)
    String description;

    BigDecimal priceDay;

    BigDecimal priceHour;

    BigDecimal priceProject;

    @NotNull
    LocalDate closingDate;

}
