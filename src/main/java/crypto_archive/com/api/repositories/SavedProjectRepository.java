package crypto_archive.com.api.repositories;

import crypto_archive.com.api.table_entities.SavedProject;
import crypto_archive.com.api.table_entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface SavedProjectRepository extends JpaRepository<SavedProject, Integer> {
    Optional<List<SavedProject>> findByUser(User user);
}
