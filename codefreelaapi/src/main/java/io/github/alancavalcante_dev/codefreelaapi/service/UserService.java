package io.github.alancavalcante_dev.codefreelaapi.service;

import io.github.alancavalcante_dev.codefreelaapi.model.User;
import io.github.alancavalcante_dev.codefreelaapi.repository.UserRepository;
import io.github.alancavalcante_dev.codefreelaapi.validate.UserValidate;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final UserValidate userValidate;
    private final PasswordEncoder encoder;

    public Optional<User> getUserByLogin(String login){
        return userRepository.findByUsername(login);
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public User save(User user) {
        var password = user.getPassword();
        user.setPassword(encoder.encode(password));
        userValidate.save(user);
        return userRepository.save(user);
    }

    public Optional<User> getUserById(String id) {
        return userRepository.findById(UUID.fromString(id));
    }

    public User getByEmail(String email) {
        return userRepository.findByEmail(email);
    }

}
