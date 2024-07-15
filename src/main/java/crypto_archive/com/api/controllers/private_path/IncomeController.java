package crypto_archive.com.api.controllers.private_path;

import crypto_archive.com.api.requests.DepositRequest;
import crypto_archive.com.api.requests.IncomeRequest;
import crypto_archive.com.api.responses.DepositResponse;
import crypto_archive.com.api.responses.IncomeResponse;
import crypto_archive.com.api.services.DepositService;
import crypto_archive.com.api.services.IncomeService;
import crypto_archive.com.api.services.ResourceNotFoundException;
import crypto_archive.com.api.table_entities.Income;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
@RequestMapping(value = "${base-path}/income")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class IncomeController {
    @Autowired
    private IncomeService incomeService;

    @GetMapping
    public ResponseEntity<Set<IncomeResponse>> getAllIncomesForAccounts(@RequestParam Integer accountId) {
        try {
            Set<IncomeResponse> incomes = incomeService.getIncomesForAccount(accountId);
            return ResponseEntity.ok(incomes);
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<IncomeResponse> createIncome(@RequestParam Integer accountId, @RequestBody IncomeRequest incomeRequest) {
        try {
            IncomeResponse depositResponse = incomeService.createIncome(accountId, incomeRequest);
            return ResponseEntity.ok(depositResponse);
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<IncomeResponse> updateIncome(@PathVariable Integer id, @RequestBody IncomeRequest incomeRequest) {
        try {
            IncomeResponse incomeResponse = incomeService.updateIncome(id, incomeRequest);
            return ResponseEntity.ok(incomeResponse);
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteIncome(@PathVariable Integer id) {
        incomeService.deleteIncome(id);
        return ResponseEntity.noContent().build();
    }
}
