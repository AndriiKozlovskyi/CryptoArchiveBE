package crypto_archive.com.api.services;

import crypto_archive.com.api.mappers.RewardMapper;
import crypto_archive.com.api.repositories.AccountRepository;
import crypto_archive.com.api.repositories.RewardRepository;
import crypto_archive.com.api.repositories.RewardRepository;
import crypto_archive.com.api.requests.RewardRequest;
import crypto_archive.com.api.responses.RewardResponse;
import crypto_archive.com.api.table_entities.Account;
import crypto_archive.com.api.table_entities.Reward;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class RewardService {
    @Autowired
    private RewardRepository rewardRepository;
    @Autowired
    private AccountRepository accountRepository;

    public Set<RewardResponse> getRewardsForAccount(Integer accountId) {
        Account account = accountRepository.findById(accountId)
                .orElseThrow(() -> new ResourceNotFoundException("Account with id: " + accountId + " not found"));

        return RewardMapper.INSTANCE.toDtos(account.getRewards());
    }

    public RewardResponse createReward(Integer accountId, RewardRequest withdrawRequest) {
        Account account = accountRepository.findById(accountId)
                .orElseThrow(() -> new ResourceNotFoundException("Account with id: " + accountId + " not found"));

        Reward withdraw = RewardMapper.INSTANCE.toEntity(withdrawRequest);
        withdraw.setAccount(account);
        Reward savedReward = rewardRepository.save(withdraw);
        account.getRewards().add(savedReward);

        rewardRepository.save(savedReward);
        accountRepository.save(account);

        return RewardMapper.INSTANCE.toDto(savedReward);
    }

    public RewardResponse updateReward(Integer withdrawId, RewardRequest withdrawRequest) {
        Reward _withdraw = rewardRepository.findById(withdrawId)
                .map(withdraw -> {
                    withdraw.setAmount(withdrawRequest.getAmount());
                    withdraw.setDate(withdrawRequest.getDate());
                    return rewardRepository.save(withdraw);
                }).orElseThrow(() -> new ResourceNotFoundException("Reward not found with id " + withdrawId));

        return RewardMapper.INSTANCE.toDto(_withdraw);
    }

    public void deleteReward(Integer id) {
        Reward withdraw = rewardRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Reward not found with id " + id));
        Account account = withdraw.getAccount();
        account.getRewards().remove(withdraw);
        accountRepository.save(account);
        rewardRepository.deleteById(id);
    }
}
