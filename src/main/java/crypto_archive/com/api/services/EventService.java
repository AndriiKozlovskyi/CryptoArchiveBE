package crypto_archive.com.api.services;

import crypto_archive.com.api.mappers.*;
import crypto_archive.com.api.repositories.*;
import crypto_archive.com.api.requests.EventRequest;
import crypto_archive.com.api.responses.*;
import crypto_archive.com.api.table_entities.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class EventService {
    @Autowired
    private EventRepository eventRepository;
    @Autowired
    private UserService userService;
    @Autowired
    private SavedEventRepository savedEventRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private TagService tagService;
    @Autowired
    SavedEventService savedEventService;

    private final String defaultStatus = "todo";

    public EventResponse createEvent(EventRequest eventRequest) {
        Event event = EventMapper.INSTANCE.toEntity(eventRequest);
        Set<Tag> tags = tagService.getTagsByIds(eventRequest.getTagsIds());
        event.setTags(tags);
        Set<User> participants = new HashSet<>();
        event.setParticipants(participants);

        Event savedEvent = eventRepository.save(event);

        return EventMapper.INSTANCE.toDto(savedEvent);
    }

    public Set<EventResponse> getAllEvents(HttpHeaders headers) {
        Set<Event> events = new HashSet<>(eventRepository.findAll());
        User user = userService.getUserFromHeaders(headers)
            .orElseThrow(() -> new ResourceNotFoundException("User not found"));
        return getEventsWithSaved(user, events);
    }

    public EventResponse getEventById(Integer id) {
        Event event = eventRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Event not found with id " + id));
        return EventMapper.INSTANCE.toDto(event);
    }

    public EventResponse updateEvent(Integer id, EventRequest eventRequest) {
        Event _event = eventRepository.findById(id)
            .map(event -> {
                event.setName(eventRequest.getName());
                event.setSrc(eventRequest.getSrc());
                event.setStartDate(eventRequest.getStartDate());
                event.setEndDate(eventRequest.getEndDate());
                event.setLink(eventRequest.getLink());
                event.setTags(tagService.getTagsByIds(eventRequest.getTagsIds()));
                return eventRepository.save(event);
            }).orElseThrow(() -> new ResourceNotFoundException("Event not found with id " + id));
        return EventMapper.INSTANCE.toDto(_event);
    }

    public void deleteEvent(Integer id, HttpHeaders headers) {
        Event event = eventRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Event not found"));
        User user = userService.getUserFromHeaders(headers)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        user.getEvents().remove(event);
        userRepository.save(user);
        eventRepository.deleteById(id);
    }

    public SavedEventResponse participateEvent(Integer eventId, HttpHeaders httpHeaders) {
        Event event = eventRepository.findById(eventId)
                .orElseThrow(() -> new ResourceNotFoundException("Event not found with id " + eventId));
        User user = userService.getUserFromHeaders(httpHeaders)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        SavedEvent savedEvent = new SavedEvent();

        savedEvent.setName(event.getName());
        savedEvent.setStartDate(event.getStartDate());
        savedEvent.setEndDate(event.getEndDate());
        savedEvent.setLink(event.getLink());
        savedEvent.setUser(user);
        savedEvent.setEvent(event);
        savedEvent.setStatus(defaultStatus);

        event.getParticipants().add(user);

        SavedEvent _savedEvent = savedEventRepository.save(savedEvent);
        event.getParticipants().add(user);

        user.getSavedEvents().add(_savedEvent);
        userRepository.save(user);
        eventRepository.save(event);

        return SavedEventMapper.INSTANCE.toDto(savedEvent);
    }

    public void unparticipateEvent(Integer eventId, HttpHeaders headers) {
        Event event = eventRepository.findById(eventId)
                .orElseThrow(() -> new ResourceNotFoundException("Event not found with id " + eventId));
        User user = userService.getUserFromHeaders(headers)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
        Set<SavedEvent> savedEvents = savedEventRepository.findByUserAndEvent(user, event).orElseThrow(() -> new ResourceNotFoundException("No Events"));
        savedEventService.deleteSavedEvent(savedEvents.iterator().next().getId(), headers);
    }

    private Set<EventResponse> getEventsWithSaved(User user, Set<Event> events) {
        Optional<Set<SavedEvent>> savedEventsOpt = savedEventRepository.findByUser(user);
        if(savedEventsOpt.isEmpty()) {
            return new HashSet<>();
        }

        Set<SavedEvent> savedEvents = savedEventsOpt.get();
        Set<Integer> savedEventsId = savedEvents.stream()
                .map(savedEvent -> {
                    if(savedEvent.getEvent() == null) {
                        return null;
                    }
                    return savedEvent.getEvent().getId();
                } )
                .collect(Collectors.toSet());

        return events.stream()
                .map(event -> {
                    EventResponse response = EventMapper.INSTANCE.toDto(event);
                    if (savedEventsId.contains(event.getId())) {
                        response.setSaved(true);
                    }
                    return response;
                })
                .collect(Collectors.toSet());
    }
}
