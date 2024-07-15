package crypto_archive.com.api.repositories;

import crypto_archive.com.api.table_entities.Income;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WithdrawRepository extends JpaRepository<Income, Integer> {

}