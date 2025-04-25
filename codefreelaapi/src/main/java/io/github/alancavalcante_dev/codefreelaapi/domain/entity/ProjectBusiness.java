package io.github.alancavalcante_dev.codefreelaapi.domain.entity;


import io.github.alancavalcante_dev.codefreelaapi.domain.user.User;
import jakarta.persistence.*;
import lombok.Data;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

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

    @ManyToOne
    @JoinColumn(name = "id_project", nullable = false)
    private Project project;

    @ManyToOne
    @JoinColumn(name = "id_client", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "id_developer")
    private User userDeveloper;


    @Column(name = "confirm_client")
    private boolean confirmClient;

    @Column(name = "confirm_developer")
    private boolean confirmDeveloper;

}