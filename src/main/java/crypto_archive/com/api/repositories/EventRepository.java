package crypto_archive.com.api.repositories;

import crypto_archive.com.api.table_entities.Event;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EventRepository extends JpaRepository<Event, Integer> {
}