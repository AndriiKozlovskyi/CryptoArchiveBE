package crypto_archive.com.api.controllers.private_path;

import crypto_archive.com.api.requests.SavedEventRequest;
import crypto_archive.com.api.requests.TaskRequest;
import crypto_archive.com.api.responses.EventResponse;
import crypto_archive.com.api.responses.SavedEventResponse;
import crypto_archive.com.api.responses.TaskResponse;
import crypto_archive.com.api.services.ResourceNotFoundException;
import crypto_archive.com.api.services.SavedEventService;
import crypto_archive.com.api.services.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
@RequestMapping(value = "${base-path}/saved_event")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class SavedEventController {
    @Autowired
    private SavedEventService savedEventService;

    @GetMapping
    public ResponseEntity<Set<SavedEventResponse>> getAllSavedEventsForEvent(@RequestHeader HttpHeaders headers) {
        try {
            Set<SavedEventResponse> savedEvents = savedEventService.allSavedEvents(headers);
            return ResponseEntity.ok(savedEvents);
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<SavedEventResponse> createSavedEvent(@RequestHeader HttpHeaders headers, @RequestBody SavedEventRequest savedEventRequest) {
        try {
            SavedEventResponse savedEvents = savedEventService.createSavedEvent(headers, savedEventRequest);
            return ResponseEntity.ok(savedEvents);
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<SavedEventResponse> updateSavedEvent(@PathVariable Integer id, @RequestBody SavedEventRequest savedEventRequest) {
        try {
            SavedEventResponse savedEvents = savedEventService.updateSavedEvent(id, savedEventRequest);
            return ResponseEntity.ok(savedEvents);
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSavedEvent(@PathVariable Integer id) {
        savedEventService.deleteSavedEvent(id);
        return ResponseEntity.noContent().build();
    }
}
