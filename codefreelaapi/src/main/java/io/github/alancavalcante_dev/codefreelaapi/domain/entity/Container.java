package io.github.alancavalcante_dev.codefreelaapi.domain.entity;

import io.github.alancavalcante_dev.codefreelaapi.domain.entity.enums.StateProject;
import jakarta.persistence.*;
import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;


@Entity
@Data
@Table(name = "tbl_container")
@EntityListeners(AuditingEntityListener.class)
public class Container {

    /**
     * A classe Container representa o controle de versionamento
     * e armazenamento dos sistema feitos pelos developers.
     * Então só é criado um registro se caso o developer e
     * o cliente entre em acordo, essa entidade é startada
     */

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id_container")
    private UUID idContainer;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_project_business", nullable = false)
    private ProjectBusiness projectBusiness;

    @Column(name = "date_last_deploy")
    private LocalDateTime dateLastDeploy;

    @Column(name = "date_starting")
    private LocalDateTime dateStarting;

    @Column(name = "date_closing")
    private LocalDateTime dateClosing;

    @CreatedDate
    @Column(name = "date_created")
    private LocalDate dateCreated;

    @Column(name = "state")
    private StateProject stateProject;
}
