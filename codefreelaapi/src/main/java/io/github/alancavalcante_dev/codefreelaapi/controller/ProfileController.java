package io.github.alancavalcante_dev.codefreelaapi.controller;

import io.github.alancavalcante_dev.codefreelaapi.dto.profile.ProfileInsertRequestDTO;
import io.github.alancavalcante_dev.codefreelaapi.dto.profile.ProfileResponseDTO;
import io.github.alancavalcante_dev.codefreelaapi.dto.profile.ProfileUpdateRequestDTO;
import io.github.alancavalcante_dev.codefreelaapi.mapperstruct.ProfileMapper;
import io.github.alancavalcante_dev.codefreelaapi.model.Profile;
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
import java.util.UUID;

@RestController
@RequestMapping("api/profile")
@RequiredArgsConstructor
@Tag(name = "Perfil de usuário")
public class ProfileController {

    private final ProfileService service;
    private final ProfileMapper mapper;


    @GetMapping
    @Operation(summary = "Pega todos os perfis")
    public ResponseEntity<List<ProfileResponseDTO>> getAllProfile() {
        List<Profile> allProfiles = service.getAllProfiles();
        List<ProfileResponseDTO> listProfileClientDTO = allProfiles.stream().
                map(mapper::toResponseDTO).toList();


        if (listProfileClientDTO.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(listProfileClientDTO);
    }

    @GetMapping("{id}")
    @Operation(summary = "Pega um perfil por Id")
    public ResponseEntity<ProfileResponseDTO> getProfile(@PathVariable("id") String id) {
        return service.getByIdProfile(UUID.fromString(id))
                .map(p -> ResponseEntity.ok(mapper.toResponseDTO(p)))
                .orElseGet( () -> ResponseEntity.notFound().build() );
    }

    @PostMapping
    @Operation(summary = "Cadastra um perfil")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<ProfileInsertRequestDTO> postProfile(@RequestBody @Valid ProfileInsertRequestDTO profile ) {
        Profile entity = mapper.toEntity(profile);
        service.save(entity);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}").buildAndExpand(entity.getIdProfile()).toUri();

        return ResponseEntity.created(location).build();
    }
//
//
//    @PutMapping("{id}")
//    @Operation(summary = "Altera o perfil por Id")
//    public ResponseEntity<ProfileResponseDTO> updateProfile(
//            @PathVariable("id") String idClient,
//            @RequestBody @Valid ProfileUpdateRequestDTO profileUpdateResponseDTO
//    ) {
//        return service.getByIdProfile(UUID.fromString(idClient))
//                .map(p -> {
//                    Profile entity = mapper.toEntityUpdate(profileUpdateResponseDTO);
//                    entity.setIdProfile(p.getIdProfile());
//                    service.update(entity, p.getUser(), p.getAddress());
//                    return ResponseEntity.ok(mapper.toResponseDTO(entity));
//                }).orElseGet(() -> ResponseEntity.notFound().build());
//    }
//
//
//
//    @DeleteMapping("{id}")
//    @Operation(summary = "Deleta um perfil")
//    public ResponseEntity<Object> deleteProfile(@PathVariable("id") String id) {
//        return service.getByIdProfile(UUID.fromString(id))
//                .map(p -> {
//                    service.delete(p);
//                    return ResponseEntity.noContent().build();
//                }).orElseGet(() -> ResponseEntity.notFound().build());
//    }
}
