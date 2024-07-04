package crypto_archive.com.api.services;

import crypto_archive.com.api.mappers.*;
import crypto_archive.com.api.repositories.*;
import crypto_archive.com.api.requests.EventRequest;
import crypto_archive.com.api.requests.ProjectRequest;
import crypto_archive.com.api.responses.*;
import crypto_archive.com.api.table_entities.*;
import crypto_archive.com.api.table_entities.Date;
import org.checkerframework.checker.units.qual.A;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
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
    private TagRepository tagRepository;
    @Autowired
    TagService tagService;
    @Autowired
    private DateRepository dateRepository;

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
                event.setTags(new HashSet<>());
                return eventRepository.save(event);
            }).orElseThrow(() -> new ResourceNotFoundException("Event not found with id " + id));
        return EventMapper.INSTANCE.toDto(_event);
    }

    public void deleteEvent(Integer id) {
        eventRepository.deleteById(id);
    }

    public void addTagToEvent(Integer eventId, Integer tagId) {
        Optional<Event> eventOpt = eventRepository.findById(eventId);
        Optional<Tag> tagOpt = tagRepository.findById(tagId);

        if (eventOpt.isPresent() && tagOpt.isPresent()) {
            Event event = eventOpt.get();
            Tag tag = tagOpt.get();

            event.getTags().add(tag);
            tag.getEvents().add(event);

            eventRepository.save(event);
            tagRepository.save(tag);
        }
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
        savedEvent.setUser(user);
        savedEvent.setEvent(event);

        event.getParticipants().add(user);

        eventRepository.save(event);
        savedEventRepository.save(savedEvent);
        eventRepository.save(event);

        return SavedEventMapper.INSTANCE.toDto(savedEvent);
    }

    public Set<TagResponse> getTagsForEvent(Integer eventId) {
        Set<Tag> tags = eventRepository.findById(eventId)
                .map(Event::getTags)
                .orElse(new HashSet<>());

        return TagMapper.INSTANCE.toDtos(tags);
    }

    public Set<Event> getEventsForTag(Integer tagId) {
        return tagRepository.findById(tagId)
                .map(Tag::getEvents)
                .orElse(new HashSet<>());
    }

    private Set<EventResponse> getEventsWithSaved(User user, Set<Event> events) {
        Optional<Set<SavedEvent>> savedEventsOpt = savedEventRepository.findByUser(user);
        if(savedEventsOpt.isEmpty()) {
            return new HashSet<>();
        }

        Set<SavedEvent> savedEvents = savedEventsOpt.get();
        Set<Integer> savedEventsId = savedEvents.stream()
                .map(savedEvent -> savedEvent.getEvent().getId())
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
