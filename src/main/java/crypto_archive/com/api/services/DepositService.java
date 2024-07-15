package crypto_archive.com.api.services;

import crypto_archive.com.api.mappers.DepositMapper;
import crypto_archive.com.api.repositories.AccountRepository;
import crypto_archive.com.api.repositories.DepositRepository;
import crypto_archive.com.api.repositories.AccountRepository;
import crypto_archive.com.api.requests.DepositRequest;
import crypto_archive.com.api.responses.DepositResponse;
import crypto_archive.com.api.table_entities.Deposit;
import crypto_archive.com.api.table_entities.Account;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Set;

@Service
public class DepositService {
    @Autowired
    private DepositRepository depositRepository;
    @Autowired
    private AccountRepository accountRepository;

    public Set<DepositResponse> getDepositsForAccount(Integer accountId) {
        Account account = accountRepository.findById(accountId)
                .orElseThrow(() -> new ResourceNotFoundException("Account with id: " + accountId + " not found"));

        return DepositMapper.INSTANCE.toDtos(account.getDeposits());
    }

    public DepositResponse createDeposit(Integer accountId, DepositRequest depositRequest) {
        Account account = accountRepository.findById(accountId)
                .orElseThrow(() -> new ResourceNotFoundException("Account with id: " + accountId + " not found"));

        Deposit deposit = DepositMapper.INSTANCE.toEntity(depositRequest);
        deposit.setAccount(account);
        Deposit savedDeposit = depositRepository.save(deposit);
        account.getDeposits().add(savedDeposit);

        depositRepository.save(savedDeposit);
        accountRepository.save(account);

        return DepositMapper.INSTANCE.toDto(savedDeposit);
    }

    public DepositResponse updateDeposit(Integer depositId, DepositRequest depositRequest) {
        Deposit _deposit = depositRepository.findById(depositId)
                .map(deposit -> {
                    deposit.setAmount(depositRequest.getAmount());
                    deposit.setDate(depositRequest.getDate());
                    return depositRepository.save(deposit);
                }).orElseThrow(() -> new ResourceNotFoundException("Deposit not found with id " + depositId));

        return DepositMapper.INSTANCE.toDto(_deposit);
    }

    public void deleteDeposit(Integer id) {
        Deposit deposit = depositRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Deposit not found with id " + id));
        Account account = deposit.getAccount();
        account.getDeposits().remove(deposit);
        accountRepository.save(account);
        depositRepository.deleteById(id);
    }
}
