package crypto_archive.com.api.controllers.private_path;

import crypto_archive.com.api.requests.TaskRequest;
import crypto_archive.com.api.responses.TaskResponse;
import crypto_archive.com.api.services.ResourceNotFoundException;
import crypto_archive.com.api.services.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
@RequestMapping(value = "${base-path}/task")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class TaskController {
    @Autowired
    private TaskService taskService;

    @GetMapping
    public ResponseEntity<Set<TaskResponse>> getAllTasksForEvent(@RequestParam Integer eventId) {
        try {
            Set<TaskResponse> tasks = taskService.getTasksForEvent(eventId);
            return ResponseEntity.ok(tasks);
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<TaskResponse> createTask(@RequestParam Integer eventId, @RequestBody TaskRequest taskRequest) {
        try {
            TaskResponse task = taskService.createTask(eventId, taskRequest);
            return ResponseEntity.ok(task);
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<TaskResponse> updateTask(@PathVariable Integer id, @RequestBody TaskRequest taskRequest) {
        try {
            TaskResponse task = taskService.updateTask(id, taskRequest);
            return ResponseEntity.ok(task);
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTask(@PathVariable Integer id) {
        taskService.deleteTask(id);
        return ResponseEntity.noContent().build();
    }
}
