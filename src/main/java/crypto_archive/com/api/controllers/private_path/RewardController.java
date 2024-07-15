package crypto_archive.com.api.controllers.private_path;

import crypto_archive.com.api.requests.RewardRequest;
import crypto_archive.com.api.responses.RewardResponse;
import crypto_archive.com.api.services.ResourceNotFoundException;
import crypto_archive.com.api.services.RewardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
@RequestMapping(value = "${base-path}/reward")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class RewardController {
    @Autowired
    private RewardService rewardService;

    @GetMapping
    public ResponseEntity<Set<RewardResponse>> getAllRewardsForAccounts(@RequestParam Integer accountId) {
        try {
            Set<RewardResponse> incomes = rewardService.getRewardsForAccount(accountId);
            return ResponseEntity.ok(incomes);
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<RewardResponse> createReward(@RequestParam Integer accountId, @RequestBody RewardRequest withdrawRequest) {
        try {
            RewardResponse withdrawResponse = rewardService.createReward(accountId, withdrawRequest);
            return ResponseEntity.ok(withdrawResponse);
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<RewardResponse> updateReward(@PathVariable Integer id, @RequestBody RewardRequest withdrawRequest) {
        try {
            RewardResponse withdrawResponse = rewardService.updateReward(id, withdrawRequest);
            return ResponseEntity.ok(withdrawResponse);
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteReward(@PathVariable Integer id) {
        rewardService.deleteReward(id);
        return ResponseEntity.noContent().build();
    }
}
