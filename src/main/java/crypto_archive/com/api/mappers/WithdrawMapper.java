package crypto_archive.com.api.mappers;

import crypto_archive.com.api.requests.WithdrawRequest;
import crypto_archive.com.api.responses.WithdrawResponse;
import crypto_archive.com.api.table_entities.Withdraw;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(uses = { ModelMapper.class, TaskMapper.class })

public interface IncomeMapper extends BaseDtoMapper<Withdraw, WithdrawRequest, WithdrawResponse> {
    IncomeMapper INSTANCE = Mappers.getMapper(IncomeMapper.class);
}
