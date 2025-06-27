package io.github.alancavalcante_dev.codefreelaapi.domain.worker.deploy.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.UUID;

@Entity
@Table(name = "tbl_port_expose")
@Data
public class PortExpose {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id_port_expose")
    private UUID idPortExpose;

    @Column(nullable = false, length = 50)
    private String port;

    @ManyToOne
    @JoinColumn(name = "id_deploy", nullable = false)
    private Deploy deploy;
}

