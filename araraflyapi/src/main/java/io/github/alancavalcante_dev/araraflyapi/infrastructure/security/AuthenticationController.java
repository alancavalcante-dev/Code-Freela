package io.github.alancavalcante_dev.araraflyapi.infrastructure.security;

import io.github.alancavalcante_dev.araraflyapi.domain.entity.User;
import io.github.alancavalcante_dev.araraflyapi.domain.entity.enums.UserRole;
import io.github.alancavalcante_dev.araraflyapi.infrastructure.security.dto.AuthenticationDTO;
import io.github.alancavalcante_dev.araraflyapi.infrastructure.security.dto.LoginResponseDTO;
import io.github.alancavalcante_dev.araraflyapi.infrastructure.security.dto.RegisterDTO;
import io.github.alancavalcante_dev.araraflyapi.exceptions.UsernameDuplicadoExeption;
import io.github.alancavalcante_dev.araraflyapi.infrastructure.repository.UserRepository;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

@Tag(name="Autenticação")
@RestController
@RequestMapping("api/auth")
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
