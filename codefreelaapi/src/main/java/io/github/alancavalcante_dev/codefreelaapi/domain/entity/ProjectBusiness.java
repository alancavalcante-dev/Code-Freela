package io.github.alancavalcante_dev.codefreelaapi.domain.entity;


import jakarta.persistence.*;
import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDate;
import java.util.UUID;

@Entity
@Data
@Table(name = "tbl_project_business")
@EntityListeners(AuditingEntityListener.class)
public class ProjectBusiness {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id_project_business")
    private UUID idProjectBusiness;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.REFRESH)
    @JoinColumn(name = "id_project", nullable = false)
    private Project project;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_developer", nullable = false)
    private User userDeveloper;

    @Column(name = "confirm_developer")
    private boolean confirmDeveloper;

    @Column(name = "confirm_client")
    private boolean confirmClient = false;

    @CreatedDate
    @Column(name = "date_match")
    private LocalDate dateMatch;
}