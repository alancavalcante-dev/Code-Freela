package io.github.alancavalcante_dev.codefreelaapi.domain.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "tbl_language")
@Data
public class Language {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id_language")
    private UUID idLanguage;

    @Column(nullable = false, length = 50)
    private String name;

    @ManyToMany(mappedBy = "languages")
    private List<PortfolioDeveloper> developers;
}

