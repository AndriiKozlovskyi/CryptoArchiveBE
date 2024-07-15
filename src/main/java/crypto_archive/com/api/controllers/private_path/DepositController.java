package crypto_archive.com.api.controllers.private_path;

import crypto_archive.com.api.requests.DepositRequest;
import crypto_archive.com.api.responses.DepositResponse;
import crypto_archive.com.api.services.DepositService;
import crypto_archive.com.api.services.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
@RequestMapping(value = "${base-path}/deposit")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class DepositController {
    @Autowired
    private DepositService depositService;

    @GetMapping
    public ResponseEntity<Set<DepositResponse>> getDepositsForAccount(@RequestParam Integer accountId) {
        try {
            Set<DepositResponse> deposits = depositService.getDepositsForAccount(accountId);
            return ResponseEntity.ok(deposits);
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<DepositResponse> createDeposit(@RequestParam Integer accountId, @RequestBody DepositRequest depositRequest) {
        try {
            DepositResponse depositResponse = depositService.createDeposit(accountId, depositRequest);
            return ResponseEntity.ok(depositResponse);
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<DepositResponse> updateDeposit(@PathVariable Integer id, @RequestBody DepositRequest depositRequest) {
        try {
            DepositResponse depositResponse = depositService.updateDeposit(id, depositRequest);
            return ResponseEntity.ok(depositResponse);
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDeposit(@PathVariable Integer id) {
        depositService.deleteDeposit(id);
        return ResponseEntity.noContent().build();
    }
}
