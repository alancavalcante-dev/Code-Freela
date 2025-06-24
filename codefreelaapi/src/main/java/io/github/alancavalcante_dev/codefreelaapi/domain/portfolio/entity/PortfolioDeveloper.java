package io.github.alancavalcante_dev.codefreelaapi.domain.portfolio.entity;


import io.github.alancavalcante_dev.codefreelaapi.domain.entity.Language;
import io.github.alancavalcante_dev.codefreelaapi.domain.entity.User;
import io.github.alancavalcante_dev.codefreelaapi.domain.profile.entity.Profile;
import jakarta.persistence.*;
import lombok.Data;

import java.util.List;
import java.util.UUID;


@Entity
@Table(name = "tbl_portfolio_developer")
@Data
public class PortfolioDeveloper {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id_developer")
    private UUID idDeveloper;

    @Column(nullable = false, length = 100)
    private String presentation;

    @Column(nullable = false, length = 180)
    private String resume;

    @ManyToMany
    @JoinTable(
            name = "tbl_portfolio_language",
            joinColumns = @JoinColumn(name = "id_developer"),
            inverseJoinColumns = @JoinColumn(name = "id_language")
    )
    private List<Language> languages;

    @OneToOne
    @JoinColumn(name = "id_user")
    private User user;

    @OneToOne(cascade = CascadeType.REFRESH)
    @JoinColumn(name = "id_profile")
    private Profile profile;
}

