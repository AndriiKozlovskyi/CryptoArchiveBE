package crypto_archive.com.api.services;

import crypto_archive.com.api.enums.ProjectStatus;
import crypto_archive.com.api.mappers.ProjectMapper;
import crypto_archive.com.api.mappers.SavedProjectMapper;
import crypto_archive.com.api.mappers.TagMapper;
import crypto_archive.com.api.repositories.ProjectRepository;
import crypto_archive.com.api.repositories.SavedProjectRepository;
import crypto_archive.com.api.repositories.TagRepository;
import crypto_archive.com.api.repositories.UserRepository;
import crypto_archive.com.api.requests.ProjectRequest;
import crypto_archive.com.api.requests.TagRequest;
import crypto_archive.com.api.responses.ProjectResponse;
import crypto_archive.com.api.responses.SavedProjectResponse;
import crypto_archive.com.api.responses.TagResponse;
import crypto_archive.com.api.table_entities.Project;
import crypto_archive.com.api.table_entities.SavedProject;
import crypto_archive.com.api.table_entities.Tag;
import crypto_archive.com.api.table_entities.User;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class ProjectService {
    @Autowired
    private ProjectRepository projectRepository;
    @Autowired
    private UserService userService;
    @Autowired
    private SavedProjectRepository savedProjectRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private TagRepository tagRepository;
    @Autowired
    private TagService tagService;

    private final String defaultStatus = "todo";

    public ProjectResponse createProject(ProjectRequest projectRequest) {
        Project project = ProjectMapper.INSTANCE.toEntity(projectRequest);
        Set<Tag> tags = tagService.getTagsByIds(projectRequest.getTagsIds());
        project.setTags(tags);

        Project savedProject = projectRepository.save(project);

        return ProjectMapper.INSTANCE.toDto(savedProject);
    }

    public Set<ProjectResponse> getAllProjects(HttpHeaders headers) {
        Set<Project> projects = new HashSet<>(projectRepository.findAll());
        User user = userService.getUserFromHeaders(headers)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        return getProjectsWithSaved(user, projects);
    }

    public ProjectResponse getProjectById(Integer id) {
        Project project = projectRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Project not found with id " + id));

        return ProjectMapper.INSTANCE.toDto(project);
    }

    public ProjectResponse updateProject(Integer id, ProjectRequest projectDetails) {
        Project _project = projectRepository.findById(id)
                .map(project -> {
                    project.setName(projectDetails.getName());
                    project.setExpenses(projectDetails.getExpenses());
                    project.setParticipants(projectDetails.getParticipants());
                    project.setSrc(projectDetails.getSrc());
                    project.setTags(new HashSet<>());
                    return projectRepository.save(project);
                }).orElseThrow(() -> new ResourceNotFoundException("Project not found with id " + id));
        return ProjectMapper.INSTANCE.toDto(_project);
    }

    public void deleteProject(Integer id) {
        projectRepository.deleteById(id);
    }

    public void addTagToProject(Integer projectId, Integer tagId) {
        Optional<Project> projectOpt = projectRepository.findById(projectId);
        Optional<Tag> tagOpt = tagRepository.findById(tagId);

        if (projectOpt.isPresent() && tagOpt.isPresent()) {
            Project project = projectOpt.get();
            Tag tag = tagOpt.get();

            project.getTags().add(tag);
            tag.getProjects().add(project);

            projectRepository.save(project);
            tagRepository.save(tag);
        }
    }

    public SavedProjectResponse saveProject(Integer projectId, HttpHeaders httpHeaders) {
        Project project = projectRepository.findById(projectId)
                .orElseThrow(() -> new ResourceNotFoundException("Project not found with id " + projectId));
        User user = userService.getUserFromHeaders(httpHeaders)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        SavedProject savedProject = new SavedProject();
        savedProject.setName(project.getName());
        savedProject.setExpenses(project.getExpenses());
        savedProject.setAmountOfAccs(1);
        savedProject.setStatus(defaultStatus);
        savedProject.setUser(user);
        savedProject.setProject(project);

        savedProjectRepository.save(savedProject);

        return SavedProjectMapper.INSTANCE.toDto(savedProject);
    }

    public Set<TagResponse> getTagsForProject(Integer projectId) {
        Set<Tag> tags = projectRepository.findById(projectId)
                .map(Project::getTags)
                .orElse(new HashSet<>());

        return TagMapper.INSTANCE.toDtos(tags);
    }

    public Set<Project> getProjectsForTag(Integer tagId) {
        return tagRepository.findById(tagId)
                .map(Tag::getProjects)
                .orElse(new HashSet<>());
    }

    private Set<ProjectResponse> getProjectsWithSaved(User user, Set<Project> projects) {
        Optional<Set<SavedProject>> savedProjectsOpt = savedProjectRepository.findByUser(user);
        if(savedProjectsOpt.isEmpty()) {
            return new HashSet<>();
        }

        Set<SavedProject> savedProjects = savedProjectsOpt.get();
        Set<Integer> savedProjectIds = savedProjects.stream()
                .map(savedProject -> savedProject.getProject().getId())
                .collect(Collectors.toSet());

        return projects.stream()
                .map(project -> {
                    ProjectResponse response = ProjectMapper.INSTANCE.toDto(project);
                    if (savedProjectIds.contains(project.getId())) {
                        response.setSaved(true);
                    }
                    return response;
                })
                .collect(Collectors.toSet());
    }
}