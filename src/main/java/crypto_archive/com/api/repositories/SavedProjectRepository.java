package crypto_archive.com.api.repositories;

import crypto_archive.com.api.table_entities.SavedProject;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SavedProjectRepository extends JpaRepository<SavedProject, Integer> {
}
