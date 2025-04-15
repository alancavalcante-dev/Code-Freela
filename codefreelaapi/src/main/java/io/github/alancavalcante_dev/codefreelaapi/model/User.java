package io.github.alancavalcante_dev.codefreelaapi.model;

import io.hypersistence.utils.hibernate.type.array.ListArrayType;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import lombok.Data;
import org.hibernate.annotations.Type;

import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "tbl_user")
@Data
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id_user", unique = true)
    private UUID idUser;

    @Column(length = 100, nullable = false, unique = true)
    private String username;

    @Column(length = 254, nullable = false)
    private String password;

    @Email
    @Column(nullable = false, length = 100)
    private String email;

    @Column(name = "roles", columnDefinition = "varchar[]")
    @Type(ListArrayType.class)
    List<String> roles;

    @Column(name = "is_active", nullable = false)
    private boolean isActive;

}