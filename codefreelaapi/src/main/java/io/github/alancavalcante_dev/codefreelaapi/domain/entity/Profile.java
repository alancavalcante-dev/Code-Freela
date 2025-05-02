package io.github.alancavalcante_dev.codefreelaapi.domain.entity;

import jakarta.persistence.*;
import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "tbl_profile")
@Data
@EntityListeners(AuditingEntityListener.class)
public class Profile {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id_profile")
    private UUID idProfile;

    @Column(length = 100, nullable = false)
    private String name;

    @Column(length = 100, nullable = false, unique = true)
    private String email;

    @Column(length = 13)
    private String phone;

    @Column(length = 11, nullable = false, unique = true)
    private String cpf;

    @Column(nullable = false, name = "is_developer")
    private Boolean isDeveloper;

    @CreatedDate
    @Column(updatable = false)
    private LocalDate dateCreated;

    @LastModifiedDate
    private LocalDateTime dateLastModify;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "id_address")
    private Address address;

    @OneToOne(cascade = CascadeType.REMOVE)
    @JoinColumn(name = "id_user")
    private User user;

    private BigDecimal balance;


}
