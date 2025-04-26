package io.github.alancavalcante_dev.codefreelaapi.presentation.controller;

import io.github.alancavalcante_dev.codefreelaapi.domain.user.User;
import io.github.alancavalcante_dev.codefreelaapi.presentation.dto.profile.ProfileInsertRequestDTO;
import io.github.alancavalcante_dev.codefreelaapi.presentation.dto.profile.ProfileResponseDTO;
import io.github.alancavalcante_dev.codefreelaapi.presentation.dto.profile.ProfileUpdateRequestDTO;
import io.github.alancavalcante_dev.codefreelaapi.mapper.ProfileMapper;
import io.github.alancavalcante_dev.codefreelaapi.domain.entity.Profile;
import io.github.alancavalcante_dev.codefreelaapi.infrastructure.security.UserLogged;
import io.github.alancavalcante_dev.codefreelaapi.domain.profile.ProfileService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("api")
@RequiredArgsConstructor
@Tag(name = "Perfil de usuário")
public class ProfileController {


    private final ProfileService service;
    private final ProfileMapper mapper;
    private final UserLogged logged;


    @GetMapping("admin/profiles")
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

    @DeleteMapping("admin/profiles/{id}")
    @Operation(summary = "Deleta um perfil")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Object> deleteProfile(@PathVariable("id") String id) {
        return service.getByIdProfile(UUID.fromString(id))
                .map(p -> {
                    service.delete(p);
                    return ResponseEntity.noContent().build();
                }).orElseGet(() -> ResponseEntity.notFound().build());
    }



    @GetMapping("user/profiles")
    @Operation(summary = "Consulta o próprio perfil")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<ProfileResponseDTO> getMyProfile() {
        return service.getProfileByIdUser(logged.load())
                .map(p -> ResponseEntity.ok(mapper.toResponseDTO(p)))
                .orElseGet( () -> ResponseEntity.notFound().build() );
    }


    @PostMapping("user/profiles")
    @Operation(summary = "Cadastra um perfil")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<Void> registerMyProfile(@RequestBody @Valid ProfileInsertRequestDTO profile) {
        Profile entity = mapper.toEntity(profile);
        service.register(entity);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}").buildAndExpand(entity.getIdProfile()).toUri();

        return ResponseEntity.created(location).build();
    }


    @PutMapping("user/profiles")
    @Operation(summary = "Altera o próprio perfil")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<ProfileResponseDTO> updateProfile(
            @RequestBody @Valid ProfileUpdateRequestDTO profileUpdateResponseDTO
    ) {
        Optional<Profile> profile = service.getProfileByIdUser(logged.load());
        if (profile.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        Profile saved = service.update(profile.get());
        return ResponseEntity.ok(mapper.toResponseDTO(saved));
    }


}
