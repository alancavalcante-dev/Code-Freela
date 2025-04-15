package io.github.alancavalcante_dev.codefreelaapi.dto.businesproject;

import io.github.alancavalcante_dev.codefreelaapi.dto.StateBusiness;
import jakarta.validation.constraints.*;

import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@ToString
@AllArgsConstructor
public class BusinessProjectInsertDTO {

    @NotNull
    @NotBlank
    String idProfile;

    @NotNull
    @NotBlank
    @Size(min=2, message = "Title: Tem que ter no minímo de 2 caracteres e no máximo de 100 caracteres", max = 100)
    String title;

    @NotNull
    @NotBlank
    @Size(min=2, message = "Description: Tem que ter no minímo de 2 caracteres e no máximo de 100 caracteres", max = 100)
    String description;


    BigDecimal priceDay;

    BigDecimal priceHour;

    BigDecimal priceProject;

    @NotNull
    LocalDate closingDate;





}
