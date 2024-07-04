package crypto_archive.com.api.mappers;

import crypto_archive.com.api.requests.DateRequest;
import crypto_archive.com.api.responses.DateResponse;
import crypto_archive.com.api.table_entities.Date;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(uses = { ModelMapper.class, SavedEventMapper.class})
public interface DateMapper extends BaseDtoMapper<Date, DateRequest, DateResponse> {
    DateMapper INSTANCE = Mappers.getMapper(DateMapper.class);
}
