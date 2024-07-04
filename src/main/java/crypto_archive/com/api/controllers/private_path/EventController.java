package crypto_archive.com.api.controllers.private_path;

import crypto_archive.com.api.requests.EventRequest;
import crypto_archive.com.api.responses.EventResponse;
import crypto_archive.com.api.responses.TagResponse;
import crypto_archive.com.api.services.EventService;
import crypto_archive.com.api.services.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
@RequestMapping(value = "${base-path}/event")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class EventController {
    @Autowired
    private EventService eventService;

    @GetMapping
    public ResponseEntity<Set<EventResponse>> getAllEvents(@RequestHeader HttpHeaders headers) {
        Set<EventResponse> events = eventService.getAllEvents(headers);
        return ResponseEntity.ok(events);
    }

    @GetMapping("/{id}")
    public ResponseEntity<EventResponse> getEventById(@PathVariable Integer id) {
        try {
            EventResponse event = eventService.getEventById(id);
            return ResponseEntity.ok(event);
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<EventResponse> createEvent(@RequestBody EventRequest eventRequest) {
        EventResponse createdEvent = eventService.createEvent(eventRequest);
        return ResponseEntity.ok(createdEvent);
    }

    @PostMapping("/{eventId}/participate")
    public ResponseEntity<?> participateEvent(@PathVariable Integer eventId, @RequestHeader HttpHeaders headers) {
        try {
            return new ResponseEntity<>(eventService.participateEvent(eventId, headers), HttpStatus.OK);
        } catch (ResourceNotFoundException e) {
            return new ResponseEntity<>(e, HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<EventResponse> updateEvent(@PathVariable Integer id, @RequestBody EventRequest eventRequest) {
        try {
            EventResponse updateEvent = eventService.updateEvent(id, eventRequest);
            return ResponseEntity.ok(updateEvent);
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEvent(@PathVariable Integer id) {
        eventService.deleteEvent(id);
        return ResponseEntity.noContent().build();
    }
}
