package io.github.alancavalcante_dev.codefreelaapi.domain.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;


@Entity
@Data
@Table(name = "tbl_container")
public class Container {

    /**
     * A classe Container representa o controle de versionamento e armazenamento dos sistema feitos pelos developers.
     *
     * Então só é criado um registro se caso o developer e o cliente entre em acordo, essa entidade é startada
     */

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id_container")
    private UUID idContainer;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_project", nullable = false)
    private ProjectBusiness projectBusiness;

    @Column(name = "date_last_deploy")
    private LocalDateTime dateLastDeploy;

    @Column(name = "date_starting")
    private LocalDateTime dateStarting;

    @Column(name = "date_closing")
    private LocalDateTime dateClosing;
}
