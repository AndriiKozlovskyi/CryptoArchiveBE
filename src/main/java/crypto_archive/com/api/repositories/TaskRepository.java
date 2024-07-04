package crypto_archive.com.api.repositories;

import crypto_archive.com.api.table_entities.Task;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TaskRepository extends JpaRepository<Task, Integer> {
}