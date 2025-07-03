package io.github.alancavalcante_dev.araraflyapi.domain.project.entity;


import io.github.alancavalcante_dev.araraflyapi.domain.entity.User;
import io.github.alancavalcante_dev.araraflyapi.domain.entity.enums.StateBusiness;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Entity
@Data
@Table(name = "tbl_project")
@EqualsAndHashCode(of = "idProject")
@ToString
@EntityListeners(AuditingEntityListener.class)
public class Project {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id_project")
    private UUID idProject;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_user", nullable = false)
    private User user;

    @Column(name = "title", length = 100, nullable = false)
    private String title;

    @Column(name = "description", length = 100, nullable = false)
    private String description;

    @Column(name = "price_day", precision = 8, scale = 2)
    private BigDecimal priceDay;

    @Column(name = "price_hour", precision = 8, scale = 2)
    private BigDecimal priceHour;

    @Column(name = "price_project", precision = 8, scale = 2)
    private BigDecimal priceProject;

    @Column(name = "closing_date")
    private LocalDate closingDate;

    @Enumerated(EnumType.STRING)
    @Column(name = "state", nullable = false)
    private StateBusiness stateBusiness;

    @CreatedDate
    @Column(name = "date_created")
    private LocalDateTime dateCreated;


    public Map<String, BigDecimal> getPriceExists() {
        Map<String, BigDecimal> prices = new HashMap<>();

        if (priceDay != null) {
            prices.put("p / Dia", priceDay);
            return prices;
        }

        if (priceHour != null) {
            prices.put("p / Hora", priceHour);
            return prices;
        }

        if (priceProject != null) {
            prices.put("p / Projeto", priceProject);
            return prices;
        }

        throw new RuntimeException("Nenhum preço em Preços do projeto");
    }
}
