package crypto_archive.com.api.services;


import crypto_archive.com.api.enums.Role;
import crypto_archive.com.api.repositories.UserRepository;
import crypto_archive.com.api.requests.AuthRequest;
import crypto_archive.com.api.requests.RegisterRequest;
import crypto_archive.com.api.responses.AuthResponse;
import crypto_archive.com.api.table_entities.User;
import jakarta.mail.MessagingException;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final UserRepository repository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final EmailService emailService;

    public AuthResponse register(RegisterRequest request) {
        if (repository.findByUsername(request.getUsername()).isPresent()) {
            throw new RuntimeException("User with username " + request.getUsername() + " already exists");
        }

        if (repository.findByEmail(request.getEmail()).isPresent()) {
            throw new RuntimeException("User with email " + request.getEmail() + " already exists");
        }

        if (request.getRole() == null) {
            request.setRole(Role.USER);
        }
        var user = User.builder()
                .email(request.getEmail())
                .username(request.getUsername())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(request.getRole())
                .build();
        repository.save(user);
        var jwtToken = jwtService.generateToken(user);

        return AuthResponse.builder()
                .token(jwtToken)
                .build();
    }

    public boolean emailExists(String email) {
        return repository.existsByEmail(email);
    }

    public boolean usernameExists(String username) {
        return repository.existsByUsername(username);
    }

    public AuthResponse authenticate(AuthRequest request) {
        authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(
                    request.getIdentifier(),
                    request.getPassword()
            )
        );

        var user = repository.findByUsername(request.getIdentifier())
                .or(() -> repository.findByEmail(request.getIdentifier()))
                .orElseThrow(() -> new EntityNotFoundException("User not found"));

        var jwtToken = jwtService.generateToken(user);

        return AuthResponse.builder()
                .token(jwtToken)
                .build();
    }
}
