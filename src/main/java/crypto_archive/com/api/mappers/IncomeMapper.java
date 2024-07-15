package crypto_archive.com.api.mappers;
import crypto_archive.com.api.requests.IncomeRequest;
import crypto_archive.com.api.responses.IncomeResponse;
import crypto_archive.com.api.table_entities.Income;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(uses = { ModelMapper.class, TaskMapper.class })

public interface IncomeMapper extends BaseDtoMapper<Income, IncomeRequest, IncomeResponse> {
    IncomeMapper INSTANCE = Mappers.getMapper(IncomeMapper.class);
}
