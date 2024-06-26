package crypto_archive.com.api.services;

import crypto_archive.com.api.repositories.UserRepository;
import crypto_archive.com.api.table_entities.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {
    @Autowired
    UserRepository repository;

    @Autowired
    JwtService jwtService;

    public Optional<User> getUserFromHeaders(HttpHeaders headers) {

        String token = headers.getFirst(HttpHeaders.AUTHORIZATION);

        assert token != null;

        String final_token = token.substring(7);

        String username = jwtService.extractUsername(final_token);

        return repository.findByUsername(username);
    }
}
