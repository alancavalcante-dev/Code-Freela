package io.github.alancavalcante_dev.araraflyapi.infrastructure.security.dto;

import io.github.alancavalcante_dev.araraflyapi.domain.entity.Address;
import io.github.alancavalcante_dev.araraflyapi.domain.profile.dto.ProfileInsertDTO;
import io.github.alancavalcante_dev.araraflyapi.domain.profile.entity.Profile;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public record RegisterDTO(
        @NotNull
        @NotBlank
        String login,

        @NotNull
        @NotBlank
        String password,

        ProfileInsertDTO profile
)
{
    public Profile dtoToEntity() {
        Profile p = new Profile();
        p.setName(profile.getName());
        p.setEmail(profile.getEmail());
        p.setPhone(profile.getPhone());
        p.setCpf(profile.getCpf());
        p.setIsDeveloper(profile.getIsDeveloper());
        p.setBalance(BigDecimal.valueOf(0.0));

        Address address = new Address();
        address.setState(profile.getAddress().getState());
        address.setCity(profile.getAddress().getCity());
        address.setNeighborhood(profile.getAddress().getNeighborhood());
        address.setAddress(profile.getAddress().getAddress());
        address.setAddressNumber(profile.getAddress().getAddressNumber());
        p.setAddress(address);

        return p;
    }

}
