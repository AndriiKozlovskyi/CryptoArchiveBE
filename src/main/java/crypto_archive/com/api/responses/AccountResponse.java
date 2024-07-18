package crypto_archive.com.api.responses;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AccountResponse {
    private Integer id;
    private String name;
    private Set<DepositResponse> deposits = new HashSet<>();
    private Set<WithdrawResponse> withdraws = new HashSet<>();
    private Set<RewardResponse> rewards = new HashSet<>();
    private Set<TaskResponse> tasks = new HashSet<>();
}
