package io.github.alancavalcante_dev.codefreelaapi.commom.validate;


import io.github.alancavalcante_dev.codefreelaapi.exceptions.CurrentDateGreaterThanProjectDate;
import io.github.alancavalcante_dev.codefreelaapi.exceptions.SomeValueMustBeFilled;
import io.github.alancavalcante_dev.codefreelaapi.domain.entity.BusinessProject;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDate;

@Component
public class BusinessProjectValidate {

    public void fieldsPrice(BusinessProject project) {
        BigDecimal priceDay = project.getPriceDay() != null ? project.getPriceDay() : BigDecimal.ZERO;
        BigDecimal priceHour = project.getPriceHour() != null ? project.getPriceHour() : BigDecimal.ZERO;
        BigDecimal priceProject = project.getPriceProject() != null ? project.getPriceProject() : BigDecimal.ZERO;

        boolean priceDayValid = priceDay.compareTo(BigDecimal.ZERO) > 0;
        boolean priceHourValid = priceHour.compareTo(BigDecimal.ZERO) > 0;
        boolean priceProjectValid = priceProject.compareTo(BigDecimal.ZERO) > 0;

        if (!(priceDayValid || priceHourValid || priceProjectValid)) {
            throw new SomeValueMustBeFilled(
                    "Algum valor deve ser preenchido em algum tipo de preço: Preço por dia, Preço por Hora, Preço por projeto"
            );
        }
    }


    public void verifyFieldDateClosing(LocalDate date) {
        LocalDate dateCurrent = LocalDate.now();
        if (dateCurrent.isAfter(date)) {
            throw new CurrentDateGreaterThanProjectDate(
                    "Data do projeto deve ser maior que a data atual"
            );
        }
    }

    public void save(BusinessProject project) {
        this.fieldsPrice(project);
        this.verifyFieldDateClosing(project.getClosingDate());
    }
}
