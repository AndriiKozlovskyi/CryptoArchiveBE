package crypto_archive.com.api.mappers;

import crypto_archive.com.api.requests.SavedProjectRequest;
import crypto_archive.com.api.responses.SavedProjectResponse;
import crypto_archive.com.api.table_entities.SavedProject;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(uses = { ModelMapper.class, ProjectMapper.class })
public interface SavedProjectMapper extends BaseDtoMapper<SavedProject, SavedProjectRequest, SavedProjectResponse> {
    SavedProjectMapper INSTANCE = Mappers.getMapper(SavedProjectMapper.class);
}
