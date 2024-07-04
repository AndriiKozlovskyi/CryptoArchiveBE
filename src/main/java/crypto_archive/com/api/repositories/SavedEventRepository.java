package crypto_archive.com.api.repositories;

import crypto_archive.com.api.table_entities.*;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.Set;

public interface SavedEventRepository extends JpaRepository<SavedEvent, Integer> {
    Optional<Set<SavedEvent>> findByUser(User user);
    Optional<Set<SavedEvent>> findByEvent(Event event);

    Optional<Set<SavedEvent>> findByUserAndEvent(User user, Event event);

}