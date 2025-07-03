package io.github.alancavalcante_dev.araraflyapi.domain.project.controller;

import io.github.alancavalcante_dev.araraflyapi.domain.project.entity.Project;
import org.springframework.data.jpa.domain.Specification;

import java.math.BigDecimal;
import java.time.LocalDate;


public class ProjectSpecification {

    public static Specification<Project> stateBusinessOpen() {
        return (root, query, cb) -> cb.equal(root.get("stateBusiness"), "OPEN");
    }


    public static Specification<Project> hasTitle(String title){
        return (root, query, cb) -> {
            if (title == null || title.isBlank()) return null;
            return cb.like(root.get("title"), "%" + title + "%");
        };
    }

    public static Specification<Project> hasDescription(String description) {
        return (root, query, cb) -> {
            if (description == null || description.isBlank()) return null;
            return cb.like(root.get("description"), "%" + description + "%");
        };
    }

    public static Specification<Project> gtaOrEqualPriceDay(BigDecimal priceDay) {
        return (root, query, cb) -> {
            if (priceDay == null) return null;
            return cb.greaterThanOrEqualTo(root.get("priceDay"), priceDay);
        };
    }

    public static Specification<Project> gtaOrEqualPriceHour(BigDecimal priceHour) {
        return (root, query, cb) -> {
            if (priceHour == null) return null;
            return cb.greaterThanOrEqualTo(root.get("priceHour"), priceHour);
        };
    }

    public static Specification<Project> gtaOrEqualPriceProject(BigDecimal priceProject) {
        return (root, query, cb) -> {
            if (priceProject == null) return null;
            return cb.greaterThanOrEqualTo(root.get("priceProject"), priceProject);
        };
    }

    public static Specification<Project> gtaOrEqualClosingDate(LocalDate closingDate) {
        return (root, query, cb) -> {
            if (closingDate == null) return null;
            return cb.greaterThanOrEqualTo(root.get("closingDate"), closingDate);
        };
    }



}