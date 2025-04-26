package io.github.alancavalcante_dev.codefreelaapi.domain.entity;


import io.github.alancavalcante_dev.codefreelaapi.domain.entity.enums.StateBusiness;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
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

    @ManyToOne
    @JoinColumn(name = "id_user", nullable = false)
    private User user;

    @OneToMany(mappedBy = "project", cascade = CascadeType.REMOVE)
    private List<ProjectBusiness> projectBusinesses = new ArrayList<>();

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
    private LocalDate dateCreated;

}
