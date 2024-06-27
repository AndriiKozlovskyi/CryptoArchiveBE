package crypto_archive.com.api.controllers.private_path;

import crypto_archive.com.api.requests.SavedProjectRequest;
import crypto_archive.com.api.responses.ProjectResponse;
import crypto_archive.com.api.responses.SavedProjectResponse;
import crypto_archive.com.api.services.ResourceNotFoundException;
import crypto_archive.com.api.services.SavedProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "${base-path}/saved_project")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class SavedProjectController {
    @Autowired
    SavedProjectService savedProjectService;

    @GetMapping("")
    public ResponseEntity<?> getAllSavedProjects(@RequestHeader HttpHeaders headers) {
        try {
            List<SavedProjectResponse> response = savedProjectService.getAllProjects(headers);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/{projectId}/unsave")
    public ResponseEntity<?> unsaveProject(@PathVariable Integer projectId, @RequestHeader HttpHeaders headers) {
        savedProjectService.unsaveProject(projectId, headers);
        return new ResponseEntity<>("OK", HttpStatus.OK);
    }

    @PutMapping("/{projectId}")
    public ResponseEntity<?> updateProject(@PathVariable Integer projectId, @RequestBody SavedProjectRequest savedProjectRequest) {
        try {
            return new ResponseEntity<>(savedProjectService.updateProject(projectId, savedProjectRequest), HttpStatus.OK);
        } catch (ResourceNotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
        }
}
