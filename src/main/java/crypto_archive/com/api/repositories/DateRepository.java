package crypto_archive.com.api.repositories;

import crypto_archive.com.api.table_entities.Date;
import crypto_archive.com.api.table_entities.SavedEvent;
import crypto_archive.com.api.table_entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.Set;

public interface DateRepository extends JpaRepository<Date, Integer> {

}