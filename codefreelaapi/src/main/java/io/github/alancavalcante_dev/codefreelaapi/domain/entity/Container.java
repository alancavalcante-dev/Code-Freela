package io.github.alancavalcante_dev.codefreelaapi.domain.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;


@Entity
@Data
@Table(name = "tbl_container")
public class Container {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id_container")
    private UUID idContainer;

    @ManyToOne
    @JoinColumn(name = "id_user", nullable = false)
    private User user;

    @OneToOne
    @JoinColumn(name = "id_project", nullable = false)
    private Project project;

    @Column(name = "title", length = 50, nullable = false)
    private String title;

    @Column(name = "description", length = 50, nullable = false)
    private String description;

    @Column(name = "date_last_deploy")
    private LocalDateTime dateLastDeploy;

    @Column(name = "date_closing")
    private LocalDateTime dateClosing;

    @Column(name = "date_starting")
    private LocalDateTime dateStarting;

    @Column(name = "date_ending")
    private LocalDateTime dateEnding;
}
