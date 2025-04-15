package io.github.alancavalcante_dev.codefreelaapi.specifications;

import io.github.alancavalcante_dev.codefreelaapi.model.BusinessProject;
import org.hibernate.mapping.Join;
import org.springframework.data.jpa.domain.Specification;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public class BusinessProjectSpecification {

    public static Specification<BusinessProject> hasTitle(String title){
        return (root, query, cb) -> {
            if (title == null || title.isBlank()) return null;
            return cb.like(root.get("title"), "%" + title + "%");
        };
    }

    public static Specification<BusinessProject> hasDescription(String description) {
        return (root, query, cb) -> {
            if (description == null || description.isBlank()) return null;
            return cb.like(root.get("description"), "%" + description + "%");
        };
    }

    public static Specification<BusinessProject> gtaOrEqualPriceDay(BigDecimal priceDay) {
        return (root, query, cb) -> {
            if (priceDay == null) return null;
            return cb.greaterThanOrEqualTo(root.get("priceDay"), priceDay);
        };
    }

    public static Specification<BusinessProject> gtaOrEqualPriceHour(BigDecimal priceHour) {
        return (root, query, cb) -> {
            if (priceHour == null) return null;
            return cb.greaterThanOrEqualTo(root.get("priceHour"), priceHour);
        };
    }

    public static Specification<BusinessProject> gtaOrEqualPriceProject(BigDecimal priceProject) {
        return (root, query, cb) -> {
            if (priceProject == null) return null;
            return cb.greaterThanOrEqualTo(root.get("priceProject"), priceProject);
        };
    }

    public static Specification<BusinessProject> gtaOrEqualClosingDate(LocalDate closingDate) {
        return (root, query, cb) -> {
            if (closingDate == null) return null;
            return cb.greaterThanOrEqualTo(root.get("closingDate"), closingDate);
        };
    }



}
