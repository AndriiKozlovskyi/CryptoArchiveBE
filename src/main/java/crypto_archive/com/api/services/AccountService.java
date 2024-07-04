package crypto_archive.com.api.services;

import crypto_archive.com.api.mappers.TaskMapper;
import crypto_archive.com.api.repositories.SavedEventRepository;
import crypto_archive.com.api.repositories.TaskRepository;
import crypto_archive.com.api.responses.TaskResponse;
import crypto_archive.com.api.table_entities.SavedEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class AccountService {
    @Autowired
    TaskRepository taskRepository;
    @Autowired
    SavedEventRepository savedEventRepository;

    public Set<TaskResponse> getAccountsForSavedEvent(Integer savedEventId) {
        SavedEvent savedEvent = savedEventRepository.findById(savedEventId)
                .orElseThrow(() -> new ResourceNotFoundException("SavedEvent with id: " + savedEventId + " not found"));

        return TaskMapper.INSTANCE.toDtos(savedEvent.getTasks());
    }
}
