package crypto_archive.com.api.services;

import crypto_archive.com.api.mappers.ProjectMapper;
import crypto_archive.com.api.mappers.TagMapper;
import crypto_archive.com.api.repositories.ProjectRepository;
import crypto_archive.com.api.repositories.TagRepository;
import crypto_archive.com.api.requests.ProjectRequest;
import crypto_archive.com.api.responses.ProjectResponse;
import crypto_archive.com.api.responses.TagResponse;
import crypto_archive.com.api.table_entities.Project;
import crypto_archive.com.api.table_entities.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class ProjectService {
    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private TagRepository tagRepository;

    public ProjectResponse createProject(ProjectRequest projectRequest) {
        System.out.println(projectRequest.toString());
        Project project = ProjectMapper.INSTANCE.toEntity(projectRequest);
        System.out.println(project);
        Project savedProject = projectRepository.save(project);
        return ProjectMapper.INSTANCE.toDto(savedProject);
    }

    public List<ProjectResponse> getAllProjects() {
        List<Project> projects = projectRepository.findAll();
        return ProjectMapper.INSTANCE.toDtos(projects);
    }

    public ProjectResponse getProjectById(Integer id) {
        Project project = projectRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Project not found with id " + id));

        return ProjectMapper.INSTANCE.toDto(project);
    }

    public ProjectResponse updateProject(Integer id, Project projectDetails) {
         Project _project = projectRepository.findById(id)
                .map(project -> {
                    project.setName(projectDetails.getName());
                    project.setExpenses(projectDetails.getExpenses());
                    project.setParticipants(projectDetails.getParticipants());
                    project.setSrc(projectDetails.getSrc());
                    project.setTags(projectDetails.getTags());
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

    public List<TagResponse> getTagsForProject(Integer projectId) {
        Set<Tag> tags = projectRepository.findById(projectId)
                .map(Project::getTags)
                .orElse(new HashSet<>());

        return TagMapper.INSTANCE.toDtos(tags.stream().toList());
    }

    public Set<Project> getProjectsForTag(Integer tagId) {
        return tagRepository.findById(tagId)
                .map(Tag::getProjects)
                .orElse(new HashSet<>());
    }
}