package crypto_archive.com.api.repositories;

import crypto_archive.com.api.table_entities.Withdraw;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WithdrawRepository extends JpaRepository<Withdraw, Integer> {

}