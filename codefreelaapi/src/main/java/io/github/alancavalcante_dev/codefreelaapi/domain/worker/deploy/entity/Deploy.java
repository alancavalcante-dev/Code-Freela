package io.github.alancavalcante_dev.codefreelaapi.domain.worker.deploy.entity;

import io.github.alancavalcante_dev.codefreelaapi.domain.container.entity.Container;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.UUID;

@Getter
@Setter
@Entity
@Table(name="tbl_deploy")
public class Deploy {

    @Id
    @Column(name = "id_deploy")
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID idDeploy;

    @Column(nullable = false, length = 50)
    private String language;

    @Column(length = 200)
    private String entrypoint;

    @OneToMany(mappedBy = "deploy", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<PortExpose> portsExposes;

    @OneToMany(mappedBy = "deploy", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Environment> variableEnvironments;

    @OneToOne(mappedBy = "deploy", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Container container;

}
