package crypto_archive.com.api.controllers.private_path;

import crypto_archive.com.api.requests.ProjectRequest;
import crypto_archive.com.api.responses.ProjectResponse;
import crypto_archive.com.api.responses.TagResponse;
import crypto_archive.com.api.services.ProjectService;
import crypto_archive.com.api.services.ResourceNotFoundException;
import crypto_archive.com.api.table_entities.Project;
import crypto_archive.com.api.table_entities.Tag;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping(value = "${base-path}/project")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class ProjectController {
    @Autowired
    private ProjectService projectService;

    @PostMapping
    public ResponseEntity<ProjectResponse> createProject(@RequestBody ProjectRequest project) {
        ProjectResponse createdProject = projectService.createProject(project);
        return ResponseEntity.ok(createdProject);
    }

    @GetMapping
    public ResponseEntity<List<ProjectResponse>> getAllProjects(@RequestHeader HttpHeaders headers) {
        List<ProjectResponse> projects = projectService.getAllProjects(headers);
        return ResponseEntity.ok(projects);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProjectResponse> getProjectById(@PathVariable Integer id) {
        try {
            ProjectResponse project = projectService.getProjectById(id);
            return ResponseEntity.ok(project);
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProjectResponse> updateProject(@PathVariable Integer id, @RequestBody ProjectRequest projectDetails) {
        try {
            ProjectResponse updatedProject = projectService.updateProject(id, projectDetails);
            return ResponseEntity.ok(updatedProject);
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProject(@PathVariable Integer id) {
        projectService.deleteProject(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{projectId}/tags/{tagId}")
    public ResponseEntity<Void> addTagToProject(@PathVariable Integer projectId, @PathVariable Integer tagId) {
        projectService.addTagToProject(projectId, tagId);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/{projectId}/save")
    public ResponseEntity<?> saveProject(@PathVariable Integer projectId, @RequestHeader HttpHeaders headers) {
        try {
            return new ResponseEntity<>(projectService.saveProject(projectId, headers), HttpStatus.OK);
        }catch (ResourceNotFoundException e) {
            return new ResponseEntity<>(e, HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/{projectId}/tags")
    public ResponseEntity<List<TagResponse>> getTagsForProject(@PathVariable Integer projectId) {
        List<TagResponse> tags = projectService.getTagsForProject(projectId);
        return ResponseEntity.ok(tags);
    }
}
