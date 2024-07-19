package crypto_archive.com.api.services;

import crypto_archive.com.api.mappers.TaskMapper;
import crypto_archive.com.api.repositories.AccountRepository;
import crypto_archive.com.api.repositories.SavedEventRepository;
import crypto_archive.com.api.repositories.TaskRepository;
import crypto_archive.com.api.requests.TaskRequest;
import crypto_archive.com.api.responses.TaskResponse;
import crypto_archive.com.api.table_entities.Account;
import crypto_archive.com.api.table_entities.Event;
import crypto_archive.com.api.table_entities.SavedEvent;
import crypto_archive.com.api.table_entities.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class TaskService {
    @Autowired
    private TaskRepository taskRepository;
    @Autowired
    private SavedEventRepository savedEventRepository;
    @Autowired
    private AccountRepository accountRepository;

    public Set<TaskResponse> getTasksForSavedEvent(Integer savedEventId) {
        SavedEvent savedEvent = savedEventRepository.findById(savedEventId)
                .orElseThrow(() -> new ResourceNotFoundException("SavedEvent with id: " + savedEventId + " not found"));

        return TaskMapper.INSTANCE.toDtos(savedEvent.getTasks());
    }

    public TaskResponse createTask(Integer savedEventId, TaskRequest taskRequest) {
        SavedEvent savedEvent = savedEventRepository.findById(savedEventId)
                .orElseThrow(() -> new ResourceNotFoundException("SavedEvent with id: " + savedEventId + " not found"));

        Task task = TaskMapper.INSTANCE.toEntity(taskRequest);
        task.setSavedEvent(savedEvent);
        Task savedTask = taskRepository.save(task);
        savedEvent.getTasks().add(savedTask);

        Set<Account> accounts = savedEvent.getAccounts();

        for (Account account : accounts) {
            Task task1 = new Task();
            task1.setHeader(savedTask.getHeader());
            task1.setAccount(account);
            task1.setSavedEvent(savedEvent);
            Task savedTask1 = taskRepository.save(task1);
            account.getTasks().add(savedTask1);
            accountRepository.save(account);
        }

        savedEventRepository.save(savedEvent);

        return TaskMapper.INSTANCE.toDto(savedTask);
    }

    public TaskResponse updateTask(Integer taskId, TaskRequest taskRequest) {
        Task _task = taskRepository.findById(taskId)
                .map(task -> {
                    task.setHeader(taskRequest.getHeader());
                    task.setDescription(taskRequest.getDescription());
                    task.setCompleted(taskRequest.isCompleted());
                    return taskRepository.save(task);
                }).orElseThrow(() -> new ResourceNotFoundException("Task not found with id " + taskId));

        return TaskMapper.INSTANCE.toDto(_task);
    }

    public void deleteTask(Integer id) {
        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Task not found with id " + id));

        SavedEvent savedEvent = task.getSavedEvent();
        savedEvent.getTasks().remove(task);

        if(task.getAccount() != null) {
            Account account = task.getAccount();
            account.getTasks().remove(task);
            accountRepository.save(account);
        }
        savedEventRepository.save(savedEvent);
        taskRepository.deleteById(id);

    }
}
