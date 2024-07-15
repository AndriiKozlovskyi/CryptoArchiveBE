package crypto_archive.com.api.services;

import crypto_archive.com.api.mappers.AccountMapper;
import crypto_archive.com.api.repositories.AccountRepository;
import crypto_archive.com.api.repositories.SavedEventRepository;
import crypto_archive.com.api.requests.AccountRequest;
import crypto_archive.com.api.requests.DepositRequest;
import crypto_archive.com.api.requests.RewardRequest;
import crypto_archive.com.api.requests.WithdrawRequest;
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
    @Autowired
    private WithdrawService withdrawService;
    @Autowired
    private DepositService depositService;
    @Autowired
    private RewardService rewardService;

    public Set<AccountResponse> getAccountsForSavedEvent(Integer savedEventId) {
        SavedEvent savedEvent = savedEventRepository.findById(savedEventId)
                .orElseThrow(() -> new ResourceNotFoundException("SavedEvent with id: " + savedEventId + " not found"));

        return AccountMapper.INSTANCE.toDtos(savedEvent.getAccounts());
    }

    public AccountResponse createAccount(Integer savedEventId, AccountRequest accountRequest) {
        SavedEvent savedEvent = savedEventRepository.findById(savedEventId)
                .orElseThrow(() -> new ResourceNotFoundException("SavedEvent with id: " + savedEventId + " not found"));

        AccountRequest initAccountRequest = new AccountRequest();
        initAccountRequest.setName(accountRequest.getName());

        Account account = AccountMapper.INSTANCE.toEntity(initAccountRequest);
        account.setSavedEvent(savedEvent);
        Account savedAccount = accountRepository.save(account);
        savedEvent.getAccounts().add(savedAccount);

        if(!accountRequest.getDeposits().isEmpty()) {
            for(DepositRequest depositRequest : accountRequest.getDeposits()) {
                depositService.createDeposit(account.getId(), depositRequest);
            }
        }
        if(!accountRequest.getWithdraws().isEmpty()) {
            for(WithdrawRequest withdrawRequest : accountRequest.getWithdraws()) {
                withdrawService.createWithdraw(account.getId(), withdrawRequest);
            }
        }
        if(!accountRequest.getRewards().isEmpty()) {
            for(RewardRequest rewardRequest : accountRequest.getRewards()) {
                rewardService.createReward(account.getId(), rewardRequest);
            }
        }
        savedEventRepository.save(savedEvent);

        return AccountMapper.INSTANCE.toDto(savedAccount);
    }

    public AccountResponse updateAccount(Integer accountId, AccountRequest accountRequest) {
        Account _account = accountRepository.findById(accountId)
                .map(account -> {
                    account.setName(accountRequest.getName());
                    return accountRepository.save(account);
                }).orElseThrow(() -> new ResourceNotFoundException("Account not found with id " + accountId));

        return AccountMapper.INSTANCE.toDto(_account);
    }

    public void deleteAccount(Integer id) {
        Account account = accountRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Account not found with id " + id));
        SavedEvent savedEvent = account.getSavedEvent();
        savedEvent.getAccounts().remove(account);
        savedEventRepository.save(savedEvent);
        accountRepository.deleteById(id);
    }
}
