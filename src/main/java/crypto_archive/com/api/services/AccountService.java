package crypto_archive.com.api.services;

import crypto_archive.com.api.mappers.AccountMapper;
import crypto_archive.com.api.mappers.TaskMapper;
import crypto_archive.com.api.repositories.AccountRepository;
import crypto_archive.com.api.repositories.SavedEventRepository;
import crypto_archive.com.api.requests.AccountRequest;
import crypto_archive.com.api.responses.AccountResponse;
import crypto_archive.com.api.table_entities.Account;
import crypto_archive.com.api.table_entities.SavedEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Set;

@Service
public class AccountService {
    @Autowired
    private AccountRepository accountRepository;
    @Autowired
    private SavedEventRepository savedEventRepository;

    public Set<AccountResponse> getAccountsForSavedEvent(Integer savedEventId) {
        SavedEvent savedEvent = savedEventRepository.findById(savedEventId)
                .orElseThrow(() -> new ResourceNotFoundException("SavedEvent with id: " + savedEventId + " not found"));

        return AccountMapper.INSTANCE.toDtos(savedEvent.getAccounts());
    }

    public AccountResponse createAccount(Integer savedEventId, AccountRequest accountRequest) {
        SavedEvent savedEvent = savedEventRepository.findById(savedEventId)
                .orElseThrow(() -> new ResourceNotFoundException("SavedEvent with id: " + savedEventId + " not found"));

        Account account = AccountMapper.INSTANCE.toEntity(accountRequest);
        account.setSavedEvent(savedEvent);
        Account savedAccount = accountRepository.save(account);
        savedEvent.getAccounts().add(savedAccount);

        accountRepository.save(savedAccount);
        savedEventRepository.save(savedEvent);

        return AccountMapper.INSTANCE.toDto(savedAccount);
    }

    public AccountResponse updateAccount(Integer accountId, AccountRequest accountRequest) {
        Account _account = accountRepository.findById(accountId)
                .map(account -> {
                    account.setName(accountRequest.getName());
                    account.setIncome(accountRequest.getIncome());
                    account.setOutcome(accountRequest.getOutcome());
                    return accountRepository.save(account);
                }).orElseThrow(() -> new ResourceNotFoundException("Account not found with id " + accountId));

        return AccountMapper.INSTANCE.toDto(_account);
    }

    public void deleteAccount(Integer id) {
        accountRepository.deleteById(id);
    }
}
