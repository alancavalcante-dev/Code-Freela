package io.github.alancavalcante_dev.codefreelaapi.controller;

import io.github.alancavalcante_dev.codefreelaapi.dto.profile.ProfileInsertRequestDTO;
import io.github.alancavalcante_dev.codefreelaapi.dto.profile.ProfileResponseDTO;
import io.github.alancavalcante_dev.codefreelaapi.dto.profile.ProfileUpdateRequestDTO;
import io.github.alancavalcante_dev.codefreelaapi.mapperstruct.ProfileMapper;
import io.github.alancavalcante_dev.codefreelaapi.model.Profile;
import io.github.alancavalcante_dev.codefreelaapi.model.User;
import io.github.alancavalcante_dev.codefreelaapi.security.UserLogged;
import io.github.alancavalcante_dev.codefreelaapi.service.ProfileService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("api/profiles")
@RequiredArgsConstructor
@Tag(name = "Perfil de usuário")
public class ProfileController {


    private final ProfileService service;
    private final ProfileMapper mapper;


    @GetMapping
    @Operation(summary = "Pega todos os perfis")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<ProfileResponseDTO>> getAllProfile() {
        List<Profile> allProfiles = service.getAllProfiles();
        List<ProfileResponseDTO> listProfileClientDTO = allProfiles.stream().
                map(mapper::toResponseDTO).toList();

        if (listProfileClientDTO.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(listProfileClientDTO);
    }


    @GetMapping("me")
    @Operation(summary = "Consulta o próprio perfil")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<ProfileResponseDTO> getMyProfile() {
        return service.getProfileByIdUser(UserLogged.load())
                .map(p -> ResponseEntity.ok(mapper.toResponseDTO(p)))
                .orElseGet( () -> ResponseEntity.notFound().build() );
    }


    @PostMapping("me")
    @Operation(summary = "Cadastra um perfil")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<ProfileInsertRequestDTO> registerMyProfile(@RequestBody @Valid ProfileInsertRequestDTO profile) {
        Profile entity = mapper.toEntity(profile);
        service.register(entity);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}").buildAndExpand(entity.getIdProfile()).toUri();

        return ResponseEntity.created(location).build();
    }


    @PutMapping("me")
    @Operation(summary = "Altera o próprio perfil")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<ProfileResponseDTO> updateProfile(
            @RequestBody @Valid ProfileUpdateRequestDTO profileUpdateResponseDTO
    ) {
        Optional<Profile> profile = service.getProfileByIdUser(UserLogged.load());
        if (profile.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        Profile saved = service.update(profile.get());
        return ResponseEntity.ok(mapper.toResponseDTO(saved));
    }


    @DeleteMapping("{id}")
    @Operation(summary = "Deleta um perfil")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Object> deleteProfile(@PathVariable("id") String id) {
        return service.getByIdProfile(UUID.fromString(id))
                .map(p -> {
                    service.delete(p);
                    return ResponseEntity.noContent().build();
                }).orElseGet(() -> ResponseEntity.notFound().build());
    }
}
