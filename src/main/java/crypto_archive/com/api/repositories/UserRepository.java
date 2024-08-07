package crypto_archive.com.api.repositories;

import crypto_archive.com.api.table_entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface UserRepository extends JpaRepository<User, Integer> {
    Optional<User> findByUsername(String username);

    Optional<User> findByEmail(String email);

    Set<User> findByUsernameContaining(String username);

    boolean existsByUsername(String username);

    boolean existsByEmail(String email);
}
