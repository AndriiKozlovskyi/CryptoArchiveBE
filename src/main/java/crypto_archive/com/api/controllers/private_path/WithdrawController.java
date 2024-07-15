package crypto_archive.com.api.controllers.private_path;

import crypto_archive.com.api.requests.WithdrawRequest;
import crypto_archive.com.api.responses.WithdrawResponse;
import crypto_archive.com.api.services.ResourceNotFoundException;
import crypto_archive.com.api.services.WithdrawService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
@RequestMapping(value = "${base-path}/withdraw")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class WithdrawController {
    @Autowired
    private WithdrawService withdrawService;

    @GetMapping
    public ResponseEntity<Set<WithdrawResponse>> getAllWithdrawsForAccounts(@RequestParam Integer accountId) {
        try {
            Set<WithdrawResponse> incomes = withdrawService.getWithdrawsForAccount(accountId);
            return ResponseEntity.ok(incomes);
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<WithdrawResponse> createWithdraw(@RequestParam Integer accountId, @RequestBody WithdrawRequest withdrawRequest) {
        try {
            WithdrawResponse withdrawResponse = withdrawService.createWithdraw(accountId, withdrawRequest);
            return ResponseEntity.ok(withdrawResponse);
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<WithdrawResponse> updateWithdraw(@PathVariable Integer id, @RequestBody WithdrawRequest withdrawRequest) {
        try {
            WithdrawResponse withdrawResponse = withdrawService.updateWithdraw(id, withdrawRequest);
            return ResponseEntity.ok(withdrawResponse);
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteWithdraw(@PathVariable Integer id) {
        withdrawService.deleteWithdraw(id);
        return ResponseEntity.noContent().build();
    }
}
