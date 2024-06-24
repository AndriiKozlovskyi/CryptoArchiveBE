package crypto_archive.com.api.repositories;

import crypto_archive.com.api.table_entities.Tag;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TagRepository extends JpaRepository<Tag, Integer> {
}
