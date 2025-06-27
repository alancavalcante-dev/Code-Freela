package io.github.alancavalcante_dev.codefreelaapi.domain.worker.deploy.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
    @JsonIgnore
    private UUID idEnvironment;

    @Column(nullable = false, length = 50, unique = true)
    private String variable;

    @ManyToOne
    @JoinColumn(name = "id_deploy")
    @JsonIgnore
    private Deploy deploy;
}

