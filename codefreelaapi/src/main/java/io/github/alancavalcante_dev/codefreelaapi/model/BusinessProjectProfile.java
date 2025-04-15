package io.github.alancavalcante_dev.codefreelaapi.model;


import jakarta.persistence.*;
import lombok.Data;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.util.UUID;

@Entity
@Data
@Table(name = "tbl_business_project_profile")
@EntityListeners(AuditingEntityListener.class)
public class BusinessProjectProfile {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id_business_project_profile")
    private UUID idBusinessProjectProfile;



    @ManyToOne
    @JoinColumn(name = "id_business_project", nullable = false)
    private BusinessProject businessProject;

    @ManyToOne
    @JoinColumn(name = "id_profile_chosen")
    private Profile profileChosen;

    // Opcional
    // Apos cliente e dev confirmarem projetos, para o resto vira state Finished e para os negociadores, fica Open.


    @Column(name = "confirm_client")
    private boolean confirmClient;

    @Column(name = "confirm_freela")
    private boolean confirmFreela;

}