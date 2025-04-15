package io.github.alancavalcante_dev.codefreelaapi.model;


import jakarta.persistence.*;
import lombok.Data;

import java.util.UUID;

@Entity
@Table(name = "tbl_client")
@Data
public class Client {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(length = 150, nullable = false, name = "client_id")
    private String clientId;

    @Column(length = 400, nullable = false, name = "client_secret")
    private String clientSecret;

    @Column(length = 200, nullable = false, name = "redirect_uri")
    private String redirectURI;

    @Column(length = 50, nullable = false, name = "scope")
    private String scope;

}
