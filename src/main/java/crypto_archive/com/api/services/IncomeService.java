package crypto_archive.com.api.services;

import crypto_archive.com.api.mappers.DepositMapper;
import crypto_archive.com.api.mappers.IncomeMapper;
import crypto_archive.com.api.repositories.AccountRepository;
import crypto_archive.com.api.repositories.DepositRepository;
import crypto_archive.com.api.repositories.IncomeRepository;
import crypto_archive.com.api.repositories.AccountRepository;
import crypto_archive.com.api.requests.DepositRequest;
import crypto_archive.com.api.requests.IncomeRequest;
import crypto_archive.com.api.responses.DepositResponse;
import crypto_archive.com.api.responses.IncomeResponse;
import crypto_archive.com.api.table_entities.Deposit;
import crypto_archive.com.api.table_entities.Income;
import crypto_archive.com.api.table_entities.Account;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class IncomeService {
    @Autowired
    private IncomeRepository incomeRepository;
    @Autowired
    private AccountRepository accountRepository;

    public Set<IncomeResponse> getIncomesForAccount(Integer accountId) {
        Account account = accountRepository.findById(accountId)
                .orElseThrow(() -> new ResourceNotFoundException("Account with id: " + accountId + " not found"));

        return IncomeMapper.INSTANCE.toDtos(account.getIncomes());
    }

    public IncomeResponse createIncome(Integer accountId, IncomeRequest incomeRequest) {
        Account account = accountRepository.findById(accountId)
                .orElseThrow(() -> new ResourceNotFoundException("Account with id: " + accountId + " not found"));

        Income income = IncomeMapper.INSTANCE.toEntity(incomeRequest);
        income.setAccount(account);
        Income savedIncome = incomeRepository.save(income);
        account.getIncomes().add(savedIncome);

        incomeRepository.save(savedIncome);
        accountRepository.save(account);

        return IncomeMapper.INSTANCE.toDto(savedIncome);
    }

    public IncomeResponse updateIncome(Integer incomeId, IncomeRequest incomeRequest) {
        Income _income = incomeRepository.findById(incomeId)
                .map(income -> {
                    income.setAmount(incomeRequest.getAmount());
                    income.setDate(incomeRequest.getDate());
                    return incomeRepository.save(income);
                }).orElseThrow(() -> new ResourceNotFoundException("Income not found with id " + incomeId));

        return IncomeMapper.INSTANCE.toDto(_income);
    }

    public void deleteIncome(Integer id) {
        Income income = incomeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Income not found with id " + id));
        Account account = income.getAccount();
        account.getIncomes().remove(income);
        accountRepository.save(account);
        incomeRepository.deleteById(id);
    }}
