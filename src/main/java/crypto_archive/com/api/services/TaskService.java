package crypto_archive.com.api.services;

import crypto_archive.com.api.mappers.TaskMapper;
import crypto_archive.com.api.repositories.EventRepository;
import crypto_archive.com.api.repositories.TaskRepository;
import crypto_archive.com.api.requests.TaskRequest;
import crypto_archive.com.api.responses.TaskResponse;
import crypto_archive.com.api.table_entities.Event;
import crypto_archive.com.api.table_entities.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class TaskService {
    @Autowired
    private TaskRepository taskRepository;
    @Autowired
    private EventRepository eventRepository;

    public Set<TaskResponse> getTasksForEvent(Integer eventId) {
        Event event = eventRepository.findById(eventId)
                .orElseThrow(() -> new ResourceNotFoundException("Event with id: " + eventId + " not found"));

        return TaskMapper.INSTANCE.toDtos(event.getTasks());
    }

    public TaskResponse createTask(Integer eventId, TaskRequest taskRequest) {
        Event event = eventRepository.findById(eventId)
                .orElseThrow(() -> new ResourceNotFoundException("Event with id: " + eventId + " not found"));

        Task task = TaskMapper.INSTANCE.toEntity(taskRequest);
        task.setEvent(event);
        Task savedTask = taskRepository.save(task);
        event.getTasks().add(savedTask);

        taskRepository.save(savedTask);
        eventRepository.save(event);

        return TaskMapper.INSTANCE.toDto(savedTask);
    }

    public TaskResponse updateTask(Integer taskId, TaskRequest taskRequest) {
        Task _task = taskRepository.findById(taskId)
                .map(task -> {
                    task.setHeader(taskRequest.getHeader());
                    task.setDescription(taskRequest.getDescription());
                    return taskRepository.save(task);
                }).orElseThrow(() -> new ResourceNotFoundException("Task not found with id " + taskId));

        return TaskMapper.INSTANCE.toDto(_task);
    }

    public void deleteTask(Integer id) {
        taskRepository.deleteById(id);
    }
}
