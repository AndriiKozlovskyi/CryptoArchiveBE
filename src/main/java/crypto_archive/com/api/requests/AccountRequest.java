package crypto_archive.com.api.requests;

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
    private Set<IncomeRequest> incomes = new HashSet<>();

}
