package io.github.alancavalcante_dev.araraflyapi.domain.entity;

import io.github.alancavalcante_dev.araraflyapi.domain.entity.enums.UserRole;
import io.github.alancavalcante_dev.araraflyapi.domain.profile.entity.Profile;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
import java.util.UUID;

@Entity(name = "tbl_users")
@Table(name = "tbl_users")
@Getter
@Setter
@NoArgsConstructor
@ToString
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    private String login;
    private String password;
    private UserRole role;

    @OneToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "id_profile")
    private Profile profile;

    public User(String login, String password, UserRole role){
        this.login = login;
        this.password = password;
        this.role = role;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return switch (this.role) {
            case ADMIN -> List.of(
                    new SimpleGrantedAuthority("ROLE_ADMIN"),
                    new SimpleGrantedAuthority("ROLE_DEVELOPER"),
                    new SimpleGrantedAuthority("ROLE_CLIENT")
            );
            case DEVELOPER -> List.of(new SimpleGrantedAuthority("ROLE_DEVELOPER"));
            case CLIENT -> List.of(new SimpleGrantedAuthority("ROLE_CLIENT"));
        };
    }

    @Override
    public String getUsername() {
        return login;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}

