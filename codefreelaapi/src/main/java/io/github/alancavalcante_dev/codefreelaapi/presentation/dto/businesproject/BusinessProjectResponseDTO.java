package io.github.alancavalcante_dev.codefreelaapi.presentation.dto.businesproject;

import io.github.alancavalcante_dev.codefreelaapi.domain.entity.enums.StateBusiness;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
public class BusinessProjectResponseDTO {
    String idProfile;
    String title;
    String description;
    BigDecimal priceDay;
    BigDecimal priceHour;
    BigDecimal priceProject;
    LocalDate closingDate;
    StateBusiness state;


}
