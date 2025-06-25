package io.github.alancavalcante_dev.codefreelaapi.infrastructure.security;

import io.github.alancavalcante_dev.codefreelaapi.domain.entity.User;
import io.github.alancavalcante_dev.codefreelaapi.domain.entity.enums.UserRole;
import io.github.alancavalcante_dev.codefreelaapi.infrastructure.security.dto.AuthenticationDTO;
import io.github.alancavalcante_dev.codefreelaapi.infrastructure.security.dto.LoginResponseDTO;
import io.github.alancavalcante_dev.codefreelaapi.infrastructure.security.dto.RegisterDTO;
import io.github.alancavalcante_dev.codefreelaapi.exceptions.UsernameDuplicadoExeption;
import io.github.alancavalcante_dev.codefreelaapi.infrastructure.repository.UserRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("auth")
@RequiredArgsConstructor
public class AuthenticationController {


    private final AuthenticationManager authenticationManager;
    private final UserRepository repository;
    private final TokenService tokenService;


    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> login(@RequestBody @Valid AuthenticationDTO data){
        var usernamePassword = new UsernamePasswordAuthenticationToken(data.login(), data.password());
        var auth = this.authenticationManager.authenticate(usernamePassword);

        var token = tokenService.generateToken((User) auth.getPrincipal());

        return ResponseEntity.ok(new LoginResponseDTO(token));
    }


    @PostMapping("/register")
    public ResponseEntity<Void> register(@RequestBody @Valid RegisterDTO data){
        if (repository.findByLogin(data.login()) != null) {
            throw new UsernameDuplicadoExeption("Esse username já esta sendo usado, tente outro!");
        }

        String encryptedPassword = new BCryptPasswordEncoder().encode(data.password());
        UserRole result = data.profile().getIsDeveloper() ? UserRole.DEVELOPER : UserRole.CLIENT;
        User newUser = new User(data.login(), encryptedPassword, result);
        newUser.setProfile(data.dtoToEntity());
        repository.save(newUser);

        return ResponseEntity.ok().build();
    }

}
