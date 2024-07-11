package crypto_archive.com.api.services;

import crypto_archive.com.api.mappers.SavedEventMapper;
import crypto_archive.com.api.repositories.SavedEventRepository;
import crypto_archive.com.api.repositories.UserRepository;
import crypto_archive.com.api.requests.SavedEventRequest;
import crypto_archive.com.api.responses.SavedEventResponse;
import crypto_archive.com.api.table_entities.SavedEvent;
import crypto_archive.com.api.table_entities.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class SavedEventService {
    @Autowired
    private SavedEventRepository savedEventRepository;
    @Autowired
    private UserService userService;
    @Autowired
    private UserRepository userRepository;

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

    public void deleteSavedEvent(Integer id) {
        savedEventRepository.deleteById(id);
    }
}
