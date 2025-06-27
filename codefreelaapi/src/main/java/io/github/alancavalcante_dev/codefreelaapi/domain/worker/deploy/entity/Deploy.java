package io.github.alancavalcante_dev.codefreelaapi.domain.worker.deploy.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.github.alancavalcante_dev.codefreelaapi.domain.container.entity.Container;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
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
    @JsonIgnore
    private UUID idDeploy;

    @NotNull
    @NotBlank
    @Column(name = "surname_service")
    private String surnameService;

    @NotNull
    @NotBlank
    private TypeService service;

    @NotNull
    @NotBlank
    @Column(nullable = false, length = 50)
    private String language;

    @NotNull
    @NotBlank
    @Column(length = 200)
    private String entrypoint;

    @JsonIgnore
    @OneToMany(mappedBy = "deploy")
    private List<PortExpose> portsExposes;

    @JsonIgnore
    @OneToMany(mappedBy = "deploy")
    private List<Environment> variableEnvironments;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "id_container", nullable = false)
    private Container container;

}
