package crypto_archive.com.api.repositories;

import crypto_archive.com.api.table_entities.Account;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountRepository extends JpaRepository<Account, Integer> {
}