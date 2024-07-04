package crypto_archive.com.api.repositories;

import crypto_archive.com.api.table_entities.Project;
import crypto_archive.com.api.table_entities.SavedProject;
import crypto_archive.com.api.table_entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.Set;

public interface SavedProjectRepository extends JpaRepository<SavedProject, Integer> {
    Optional<Set<SavedProject>> findByUser(User user);
    Optional<Set<SavedProject>> findByProject(Project project);

    Optional<Set<SavedProject>> findByUserAndProject(User user, Project project);

}
