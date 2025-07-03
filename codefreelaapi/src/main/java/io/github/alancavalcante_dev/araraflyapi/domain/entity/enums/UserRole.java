package io.github.alancavalcante_dev.araraflyapi.domain.entity.enums;

public enum UserRole {
    CLIENT("client"),
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

