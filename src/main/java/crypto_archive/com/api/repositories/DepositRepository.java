package crypto_archive.com.api.repositories;

import crypto_archive.com.api.table_entities.Deposit;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DepositRepository extends JpaRepository<Deposit, Integer> {

}