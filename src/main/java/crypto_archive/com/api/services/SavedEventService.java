package crypto_archive.com.api.services;

import crypto_archive.com.api.mappers.SavedEventMapper;
import crypto_archive.com.api.repositories.AccountRepository;
import crypto_archive.com.api.repositories.EventRepository;
import crypto_archive.com.api.repositories.SavedEventRepository;
import crypto_archive.com.api.repositories.UserRepository;
import crypto_archive.com.api.requests.SavedEventRequest;
import crypto_archive.com.api.responses.SavedEventResponse;
import crypto_archive.com.api.table_entities.Account;
import crypto_archive.com.api.table_entities.Event;
import crypto_archive.com.api.table_entities.SavedEvent;
import crypto_archive.com.api.table_entities.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Service
public class SavedEventService {
    @Autowired
    private SavedEventRepository savedEventRepository;
    @Autowired
    private UserService userService;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private AccountRepository accountRepository;
    @Autowired
    private EventRepository eventRepository;

    public Set<SavedEventResponse> allSavedEvents(HttpHeaders headers) {
        User user = userService.getUserFromHeaders(headers)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
        return SavedEventMapper.INSTANCE.toDtos(user.getSavedEvents());
    }

    public SavedEventResponse createSavedEvent(HttpHeaders headers, SavedEventRequest savedEventRequest) {
        User user = userService.getUserFromHeaders(headers)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        SavedEvent savedEvent = SavedEventMapper.INSTANCE.toEntity(savedEventRequest);
        savedEvent.setUser(user);

        SavedEvent _savedEvent = savedEventRepository.save(savedEvent);
        user.getSavedEvents().add(_savedEvent);

        userRepository.save(user);

        return SavedEventMapper.INSTANCE.toDto(_savedEvent);
    }

    public SavedEventResponse updateSavedEvent(Integer id, SavedEventRequest savedEventRequest) {
        System.out.println(savedEventRequest);
        SavedEvent _savedEvent = savedEventRepository.findById(id)
                .map(savedEvent -> {
                    savedEvent.setName(savedEventRequest.getName());
                    savedEvent.setStartDate(savedEventRequest.getStartDate());
                    savedEvent.setEndDate(savedEventRequest.getEndDate());
                    savedEvent.setStatus(savedEventRequest.getStatus());
                    savedEvent.setOrderNumber(savedEventRequest.getOrderNumber());
                    return savedEventRepository.save(savedEvent);
                }).orElseThrow(() -> new ResourceNotFoundException("Event not found with id " + id));
        return SavedEventMapper.INSTANCE.toDto(_savedEvent);
    }

    public void deleteSavedEvent(Integer id, HttpHeaders headers) {
        SavedEvent savedEvent = savedEventRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("SavedEvent not found"));
        User user = userService.getUserFromHeaders(headers)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        if(savedEvent.getEvent() != null) {
            user.getEvents().remove(savedEvent.getEvent());
            userRepository.save(user);
            Event event = savedEvent.getEvent();
            event.getParticipants().remove(user);
            eventRepository.save(event);
        }

        Set<Account> accounts = savedEvent.getAccounts();
        List<Account> accountsToRemove = new ArrayList<>(accounts);

        savedEvent.getAccounts().clear();

        savedEventRepository.save(savedEvent);
        for (Account account : accountsToRemove) {
            accountRepository.delete(account);
        }

        user.getSavedEvents().remove(savedEvent);
        userRepository.save(user);
        savedEventRepository.deleteById(id);
    }
}
