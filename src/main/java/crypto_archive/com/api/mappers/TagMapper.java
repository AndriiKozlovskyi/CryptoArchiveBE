package crypto_archive.com.api.mappers;

import crypto_archive.com.api.requests.TagRequest;
import crypto_archive.com.api.responses.TagResponse;
import crypto_archive.com.api.table_entities.Tag;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(uses = { ModelMapper.class, EventMapper.class })

public interface TagMapper extends BaseDtoMapper<Tag, TagRequest, TagResponse> {
    TagMapper INSTANCE = Mappers.getMapper(TagMapper.class);
}
