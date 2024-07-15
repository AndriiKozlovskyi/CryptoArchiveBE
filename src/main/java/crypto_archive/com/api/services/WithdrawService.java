package crypto_archive.com.api.services;

import crypto_archive.com.api.mappers.WithdrawMapper;
import crypto_archive.com.api.repositories.AccountRepository;
import crypto_archive.com.api.repositories.WithdrawRepository;
import crypto_archive.com.api.requests.WithdrawRequest;
import crypto_archive.com.api.responses.WithdrawResponse;
import crypto_archive.com.api.table_entities.Withdraw;
import crypto_archive.com.api.table_entities.Account;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class WithdrawService {
    @Autowired
    private WithdrawRepository withdrawRepository;
    @Autowired
    private AccountRepository accountRepository;

    public Set<WithdrawResponse> getWithdrawsForAccount(Integer accountId) {
        Account account = accountRepository.findById(accountId)
                .orElseThrow(() -> new ResourceNotFoundException("Account with id: " + accountId + " not found"));

        return WithdrawMapper.INSTANCE.toDtos(account.getWithdraws());
    }

    public WithdrawResponse createWithdraw(Integer accountId, WithdrawRequest withdrawRequest) {
        Account account = accountRepository.findById(accountId)
                .orElseThrow(() -> new ResourceNotFoundException("Account with id: " + accountId + " not found"));

        Withdraw withdraw = WithdrawMapper.INSTANCE.toEntity(withdrawRequest);
        withdraw.setAccount(account);
        Withdraw savedWithdraw = withdrawRepository.save(withdraw);
        account.getWithdraws().add(savedWithdraw);

        withdrawRepository.save(savedWithdraw);
        accountRepository.save(account);

        return WithdrawMapper.INSTANCE.toDto(savedWithdraw);
    }

    public WithdrawResponse updateWithdraw(Integer withdrawId, WithdrawRequest withdrawRequest) {
        Withdraw _withdraw = withdrawRepository.findById(withdrawId)
                .map(withdraw -> {
                    withdraw.setAmount(withdrawRequest.getAmount());
                    withdraw.setDate(withdrawRequest.getDate());
                    return withdrawRepository.save(withdraw);
                }).orElseThrow(() -> new ResourceNotFoundException("Withdraw not found with id " + withdrawId));

        return WithdrawMapper.INSTANCE.toDto(_withdraw);
    }

    public void deleteWithdraw(Integer id) {
        Withdraw withdraw = withdrawRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Withdraw not found with id " + id));
        Account account = withdraw.getAccount();
        account.getWithdraws().remove(withdraw);
        accountRepository.save(account);
        withdrawRepository.deleteById(id);
    }
}
