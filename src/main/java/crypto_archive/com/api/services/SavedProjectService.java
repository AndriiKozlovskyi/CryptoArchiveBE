package crypto_archive.com.api.services;

import crypto_archive.com.api.mappers.ProjectMapper;
import crypto_archive.com.api.mappers.SavedProjectMapper;
import crypto_archive.com.api.repositories.ProjectRepository;
import crypto_archive.com.api.repositories.SavedProjectRepository;
import crypto_archive.com.api.requests.SavedProjectRequest;
import crypto_archive.com.api.responses.ProjectResponse;
import crypto_archive.com.api.responses.SavedProjectResponse;
import crypto_archive.com.api.table_entities.Project;
import crypto_archive.com.api.table_entities.SavedProject;
import crypto_archive.com.api.table_entities.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class SavedProjectService {
    @Autowired
    private SavedProjectRepository repository;
    @Autowired
    private ProjectRepository projectRepository;
    private @Autowired
    UserService userService;

    public List<SavedProjectResponse> getAllProjects(HttpHeaders headers) {
        User user = userService.getUserFromHeaders(headers)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        return this.getProjectsWithSaved(user);
    }

    public void unsaveProject(Integer projectId, HttpHeaders headers) {
        Project project = projectRepository.findById(projectId)
                .orElseThrow(() -> new ResourceNotFoundException("Project not found with id " + projectId));
        User user = userService.getUserFromHeaders(headers)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
        List<SavedProject> savedProjects = repository.findByUserAndProject(user, project).orElseThrow(() -> new ResourceNotFoundException("No projects"));
        repository.deleteById(savedProjects.get(0).getId());
    }

    public SavedProjectResponse updateProject(Integer id, SavedProjectRequest request) {
        SavedProject savedProject = repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("SavedProject with is: " + id + " not found" ));
        savedProject.setName(request.getName());
        savedProject.setStatus(request.getStatus());
        savedProject.setAmountOfAccs(request.getAmountOfAccs());
        savedProject.setExpenses(request.getExpenses());

        repository.save(savedProject);

        return SavedProjectMapper.INSTANCE.toDto(savedProject);
    }

    private List<SavedProjectResponse> getProjectsWithSaved(User user) {
        Optional<List<SavedProject>> savedProjectsOpt = repository.findByUser(user);
        if(savedProjectsOpt.isEmpty()) {
            return new ArrayList<>();
        }

        List<SavedProject> savedProjects = savedProjectsOpt.get();

        System.out.println(savedProjects);
        return SavedProjectMapper.INSTANCE.toDtos(savedProjects);
    }
}
