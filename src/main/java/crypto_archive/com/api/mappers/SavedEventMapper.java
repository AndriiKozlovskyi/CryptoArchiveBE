package crypto_archive.com.api.mappers;

import crypto_archive.com.api.requests.SavedEventRequest;
import crypto_archive.com.api.responses.SavedEventResponse;
import crypto_archive.com.api.table_entities.SavedEvent;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(uses = { ModelMapper.class, TaskMapper.class })

public interface SavedEventMapper extends BaseDtoMapper<SavedEvent, SavedEventRequest, SavedEventResponse> {
    SavedEventMapper INSTANCE = Mappers.getMapper(SavedEventMapper.class);
}