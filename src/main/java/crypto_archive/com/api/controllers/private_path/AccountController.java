package crypto_archive.com.api.controllers.private_path;

import crypto_archive.com.api.requests.AccountRequest;
import crypto_archive.com.api.requests.EventRequest;
import crypto_archive.com.api.responses.AccountResponse;
import crypto_archive.com.api.responses.EventResponse;
import crypto_archive.com.api.services.AccountService;
import crypto_archive.com.api.services.EventService;
import crypto_archive.com.api.services.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
@RequestMapping(value = "${base-path}/account")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class AccountController {
    @Autowired
    private AccountService accountService;

    @GetMapping
    public ResponseEntity<Set<AccountResponse>> getAllAccountsForSavedEvent(@RequestParam Integer savedEventId) {
        try {
            Set<AccountResponse> accounts = accountService.getAccountsForSavedEvent(savedEventId);
            return ResponseEntity.ok(accounts);
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<AccountResponse> createAccount(@RequestParam Integer savedEventId, @RequestBody AccountRequest accountRequest) {
        try {
            AccountResponse account = accountService.createAccount(savedEventId, accountRequest);
            return ResponseEntity.ok(account);
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<AccountResponse> updateAccount(@PathVariable Integer id, @RequestBody AccountRequest accountRequest) {
        try {
            AccountResponse account = accountService.updateAccount(id, accountRequest);
            return ResponseEntity.ok(account);
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAccount(@PathVariable Integer id) {
        accountService.deleteAccount(id);
        return ResponseEntity.noContent().build();
    }
}
