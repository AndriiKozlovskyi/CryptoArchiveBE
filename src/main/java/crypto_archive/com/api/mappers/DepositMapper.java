package crypto_archive.com.api.mappers;

import crypto_archive.com.api.requests.DepositRequest;
import crypto_archive.com.api.responses.DepositResponse;
import crypto_archive.com.api.table_entities.Deposit;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(uses = { ModelMapper.class, TaskMapper.class })

public interface DepositMapper extends BaseDtoMapper<Deposit, DepositRequest, DepositResponse> {
    DepositMapper INSTANCE = Mappers.getMapper(DepositMapper.class);
}
