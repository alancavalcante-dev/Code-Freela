package io.github.alancavalcante_dev.araraflyapi.domain.worker.deploy.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.github.alancavalcante_dev.araraflyapi.domain.container.entity.Container;
import io.github.alancavalcante_dev.araraflyapi.domain.worker.deploy.service.Language;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;
import java.util.UUID;

@Getter
@Setter
@Entity
@ToString
@Table(name="tbl_deploy")
public class Deploy {

    @Id
    @Column(name = "id_deploy")
    @GeneratedValue(strategy = GenerationType.UUID)
    @JsonIgnore
    private UUID idDeploy;

    @Column(name = "surname_service", length = 100)
    private String surnameService;

    @Column(length = 100)
    @Enumerated(EnumType.STRING)
    private TypeService typeService;

    @Column(length = 100)
    @Enumerated(EnumType.STRING)
    private Language language;

    @Column(length = 100)
    private String languageVersion;

    @Column(length = 200)
    private String entrypoint;

    @JsonIgnore
    @OneToMany(mappedBy = "deploy")
    private List<PortExpose> portsExposes;

    @JsonIgnore
    @OneToMany(mappedBy = "deploy")
    private List<Environment> variableEnvironments;

    @JsonIgnore
    @Column(name = "is_up")
    private Boolean isUp;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_container", nullable = false)
    private Container container;

}
