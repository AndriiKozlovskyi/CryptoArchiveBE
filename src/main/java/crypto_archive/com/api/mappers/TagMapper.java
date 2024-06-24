package crypto_archive.com.api.mappers;

import crypto_archive.com.api.requests.ProjectRequest;
import crypto_archive.com.api.requests.TagRequest;
import crypto_archive.com.api.responses.ProjectResponse;
import crypto_archive.com.api.responses.TagResponse;
import crypto_archive.com.api.table_entities.Project;
import crypto_archive.com.api.table_entities.Tag;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(uses = { ModelMapper.class, ProjectMapper.class })

public interface TagMapper extends BaseDtoMapper<Tag, TagRequest, TagResponse> {
    TagMapper INSTANCE = Mappers.getMapper(TagMapper.class);
}
