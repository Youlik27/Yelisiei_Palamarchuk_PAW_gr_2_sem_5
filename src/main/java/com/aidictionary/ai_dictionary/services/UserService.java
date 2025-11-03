package com.aidictionary.ai_dictionary.services;

import com.aidictionary.ai_dictionary.models.Role;
import com.aidictionary.ai_dictionary.models.User;
import com.aidictionary.ai_dictionary.repositories.RoleRepository;
import com.aidictionary.ai_dictionary.repositories.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final RoleRepository roleRepository;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder, RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.roleRepository = roleRepository;
    }

    public void registerNewUser(String username, String password, String repeatPassword, String email) {

        if (!Objects.equals(password, repeatPassword)) {
            throw new IllegalArgumentException("Hasła nie są zgodne");
        }

        if (userRepository.existsByUsername(username)) {
            throw new IllegalArgumentException("Użytkownik o tej nazwie już istnieje");
        }

        if (userRepository.existsByEmail(email)) {
            throw new IllegalArgumentException("Ten adres email jest już zajęty");
        }

        String hashedPassword = passwordEncoder.encode(password);

        Role role = roleRepository.findByName("user")
                .orElseThrow(() -> new RuntimeException("Błąd krytyczny: Rola 'user' nie została znaleziona w bazie danych"));

        User newUser = new User();
        newUser.setUsername(username);
        newUser.setPassword(hashedPassword);
        newUser.setRole(role);
        newUser.setEmail(email);

        userRepository.save(newUser);
    }
}