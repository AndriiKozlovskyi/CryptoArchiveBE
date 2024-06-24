package crypto_archive.com.api.repositories;

import crypto_archive.com.api.table_entities.Project;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProjectRepository extends JpaRepository<Project, Integer> {
}