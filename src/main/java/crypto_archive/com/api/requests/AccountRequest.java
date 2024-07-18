package crypto_archive.com.api.requests;

import crypto_archive.com.api.responses.RewardResponse;
import crypto_archive.com.api.responses.TaskResponse;
import crypto_archive.com.api.table_entities.Deposit;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AccountRequest {
    private String name;
    private Set<DepositRequest> deposits = new HashSet<>();
    private Set<WithdrawRequest> withdraws = new HashSet<>();
    private Set<RewardRequest> rewards = new HashSet<>();
}
