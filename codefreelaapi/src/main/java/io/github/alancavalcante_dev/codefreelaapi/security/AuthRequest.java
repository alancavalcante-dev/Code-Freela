package io.github.alancavalcante_dev.codefreelaapi.security;

import lombok.Data;

@Data
public class AuthRequest {
    private String username;
    private String password;
}