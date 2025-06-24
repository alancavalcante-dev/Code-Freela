package io.github.alancavalcante_dev.codefreelaapi.infrastructure.security.dto;

import io.github.alancavalcante_dev.codefreelaapi.domain.entity.Address;
import io.github.alancavalcante_dev.codefreelaapi.domain.profile.dto.ProfileInsertDTO;
import io.github.alancavalcante_dev.codefreelaapi.domain.profile.entity.Profile;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

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
