package io.github.alancavalcante_dev.araraflyapi.domain.profile.controller;

import io.github.alancavalcante_dev.araraflyapi.domain.entity.Address;
import io.github.alancavalcante_dev.araraflyapi.domain.entity.dto.AddressDTO;
import io.github.alancavalcante_dev.araraflyapi.domain.profile.dto.ProfileResponseDTO;
import io.github.alancavalcante_dev.araraflyapi.domain.profile.dto.ProfileUpdateRequestDTO;
import io.github.alancavalcante_dev.araraflyapi.mapper.ProfileMapper;
import io.github.alancavalcante_dev.araraflyapi.domain.profile.entity.Profile;
import io.github.alancavalcante_dev.araraflyapi.infrastructure.security.UserLogged;
import io.github.alancavalcante_dev.araraflyapi.domain.profile.service.ProfileService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("api/user/profile")
@RequiredArgsConstructor
@Tag(name = "Perfil de usuário")
@Schema(name = "Perfil de Usuários")
public class ProfileController {


    private final ProfileService service;
    private final ProfileMapper mapper;
    private final UserLogged logged;


    @GetMapping
    @Operation(summary = "Consulta o próprio perfil")
    @PreAuthorize("hasAnyRole('CLIENT', 'DEVELOPER')")
    public ResponseEntity<ProfileResponseDTO> getMyProfile() {
        return service.getProfileByIdUser(logged.load())
                .map(p -> ResponseEntity.ok(mapper.toResponseDTO(p)))
                .orElseGet( () -> ResponseEntity.notFound().build() );
    }



    @PutMapping
    @Operation(summary = "Altera o próprio perfil")
    @PreAuthorize("hasAnyRole('CLIENT', 'DEVELOPER')")
    public ResponseEntity<ProfileResponseDTO> updateProfile( @RequestBody @Valid ProfileUpdateRequestDTO data) {

        Optional<Profile> profileOpt = service.getProfileByIdUser(logged.load());
        if (profileOpt.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        Profile profile = profileOpt.get();
        Address address = profile.getAddress();
        AddressDTO addressDTO = data.getAddress();

        address.setAddress(addressDTO.getAddress());
        address.setAddressNumber(addressDTO.getAddressNumber());
        address.setCity(addressDTO.getCity());
        address.setState(addressDTO.getState());
        address.setNeighborhood(addressDTO.getNeighborhood());

        profile.setName(data.getName());
        profile.setEmail(data.getEmail());
        profile.setPhone(data.getPhone());
        profile.setCpf(data.getCpf());
        profile.setIsDeveloper(data.getIsDeveloper());
        profile.setAddress(address);

        Profile saved = service.update(profile);
        return ResponseEntity.ok(mapper.toResponseDTO(saved));
    }


}
