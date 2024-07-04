package crypto_archive.com.api.mappers;

import crypto_archive.com.api.requests.EventRequest;
import crypto_archive.com.api.responses.EventResponse;
import crypto_archive.com.api.table_entities.Event;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(uses = { ModelMapper.class, TagMapper.class, TaskMapper.class, UserMapper.class })

public interface EventMapper extends BaseDtoMapper<Event, EventRequest, EventResponse> {
    EventMapper INSTANCE = Mappers.getMapper(EventMapper.class);
}
