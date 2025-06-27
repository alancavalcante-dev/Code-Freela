package io.github.alancavalcante_dev.codefreelaapi.domain.worker.deploy.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.UUID;

@Entity
@Table(name = "tbl_environment")
@Data
public class Environment {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id_environment")
    private UUID idEnvironment;

    @Column(nullable = false, length = 50)
    private String variable;

    @ManyToOne
    @JoinColumn(name = "id_deploy", nullable = false) // nome da FK no banco
    private Deploy deploy;
}

