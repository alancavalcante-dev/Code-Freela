package io.github.alancavalcante_dev.codefreelaapi.domain.entity.enums;

public enum UserRole {
    CLIENT("CLIENT"),
    DEVELOPER("developer"),
    ADMIN("admin");


    private String role;

    UserRole(String role){
        this.role = role;
    }

    public String getRole(){
        return role;
    }
}

