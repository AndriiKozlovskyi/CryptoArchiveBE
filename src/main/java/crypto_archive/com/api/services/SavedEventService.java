package crypto_archive.com.api.services;

import crypto_archive.com.api.mappers.DateMapper;
import crypto_archive.com.api.mappers.SavedEventMapper;
import crypto_archive.com.api.repositories.SavedEventRepository;
import crypto_archive.com.api.requests.SavedEventRequest;
import crypto_archive.com.api.responses.SavedEventResponse;
import crypto_archive.com.api.table_entities.Event;
import crypto_archive.com.api.table_entities.SavedEvent;
import crypto_archive.com.api.table_entities.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class SavedEventService {
    @Autowired
    SavedEventRepository savedEventRepository;
    @Autowired
    UserService userService;

    public Set<SavedEventResponse> allSavedEvents(HttpHeaders headers) {
        User user = userService.getUserFromHeaders(headers)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
        return SavedEventMapper.INSTANCE.toDtos(savedEventRepository.findByUser(user).get());
    }

    public SavedEventResponse createSavedEvent(HttpHeaders headers, SavedEventRequest savedEventRequest) {
        User user = userService.getUserFromHeaders(headers)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
        SavedEvent savedEvent = SavedEventMapper.INSTANCE.toEntity(savedEventRequest);
        savedEvent.setUser(user);
        return SavedEventMapper.INSTANCE.toDto(savedEvent);
    }

    public SavedEventResponse updateSavedEvent(Integer id, SavedEventRequest savedEventRequest) {
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
