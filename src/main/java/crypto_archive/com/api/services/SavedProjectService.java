package crypto_archive.com.api.services;

import crypto_archive.com.api.mappers.SavedProjectMapper;
import crypto_archive.com.api.repositories.ProjectRepository;
import crypto_archive.com.api.repositories.SavedProjectRepository;
import crypto_archive.com.api.requests.SavedProjectRequest;
import crypto_archive.com.api.responses.SavedProjectResponse;
import crypto_archive.com.api.table_entities.Project;
import crypto_archive.com.api.table_entities.SavedProject;
import crypto_archive.com.api.table_entities.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;
import java.util.Optional;


@Service
public class SavedProjectService {
    @Autowired
    private SavedProjectRepository repository;
    @Autowired
    private ProjectRepository projectRepository;
    @Autowired
    private UserService userService;

    public Set<SavedProjectResponse> getAllProjects(HttpHeaders headers) {
        User user = userService.getUserFromHeaders(headers)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        return this.getProjectsWithSaved(user);
    }

    public void unsaveProject(Integer projectId, HttpHeaders headers) {
        Project project = projectRepository.findById(projectId)
                .orElseThrow(() -> new ResourceNotFoundException("Project not found with id " + projectId));
        User user = userService.getUserFromHeaders(headers)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
        Set<SavedProject> savedProjects = repository.findByUserAndProject(user, project).orElseThrow(() -> new ResourceNotFoundException("No projects"));
        repository.deleteById(savedProjects.iterator().next().getId());
    }

    public SavedProjectResponse updateProject(Integer id, SavedProjectRequest request) {
        SavedProject savedProject = repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("SavedProject with is: " + id + " not found" ));
        savedProject.setName(request.getName());
        savedProject.setStatus(request.getStatus());
        savedProject.setAmountOfAccs(request.getAmountOfAccs());
        savedProject.setOrderNumber(request.getOrderNumber());
        savedProject.setExpenses(request.getExpenses());

        repository.save(savedProject);

        return SavedProjectMapper.INSTANCE.toDto(savedProject);
    }

    private Set<SavedProjectResponse> getProjectsWithSaved(User user) {
        Optional<Set<SavedProject>> savedProjectsOpt = repository.findByUser(user);
        if(savedProjectsOpt.isEmpty()) {
            return new HashSet<>();
        }

        Set<SavedProject> savedProjects = savedProjectsOpt.get();

        return SavedProjectMapper.INSTANCE.toDtos(savedProjects);
    }
}
