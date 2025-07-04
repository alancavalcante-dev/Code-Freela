package io.github.alancavalcante_dev.araraflyapi.domain.container.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.github.alancavalcante_dev.araraflyapi.domain.appointment.entity.Appointment;
import io.github.alancavalcante_dev.araraflyapi.domain.match.entity.ProjectBusiness;
import io.github.alancavalcante_dev.araraflyapi.domain.entity.enums.StateProject;
import io.github.alancavalcante_dev.araraflyapi.domain.worker.deploy.entity.Deploy;
import jakarta.persistence.*;
import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;


/**
 * A classe Container representa o controle de versionamento
 * e armazenamento dos sistema feitos pelos developers.
 * Então só é criado um registro se caso o developer e
 * o cliente entre em acordo, essa entidade é startada
 */


@Entity
@Data
@Table(name = "tbl_container")
@EntityListeners(AuditingEntityListener.class)
public class Container {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id_container")
    private UUID idContainer;

    @Column
    private String name;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_project_business", nullable = false)
    private ProjectBusiness projectBusiness;

    @CreatedDate
    @Column(name = "date_created")
    private LocalDate dateCreated;

    @Enumerated(EnumType.STRING)
    @Column(name = "state")
    private StateProject stateProject;

    @OneToMany(mappedBy = "container", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    private List<Appointment> appointments = new ArrayList<>();

    @JsonIgnore
    @OneToMany(mappedBy = "container", fetch = FetchType.EAGER)
    private List<Deploy> deploy;
}
