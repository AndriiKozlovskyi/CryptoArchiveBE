package crypto_archive.com.api.mappers;

import crypto_archive.com.api.repositories.ProjectRepository;
import crypto_archive.com.api.requests.ProjectRequest;
import crypto_archive.com.api.responses.ProjectResponse;
import crypto_archive.com.api.table_entities.Project;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;

@Mapper(uses = { ModelMapper.class, TagMapper.class })

public interface ProjectMapper extends BaseDtoMapper<Project, ProjectRequest, ProjectResponse> {
    ProjectMapper INSTANCE = Mappers.getMapper(ProjectMapper.class);
}
