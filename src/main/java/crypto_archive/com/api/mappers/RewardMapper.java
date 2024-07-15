package crypto_archive.com.api.mappers;

import crypto_archive.com.api.requests.RewardRequest;
import crypto_archive.com.api.responses.RewardResponse;
import crypto_archive.com.api.table_entities.Reward;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(uses = { ModelMapper.class, AccountMapper.class })
public interface RewardMapper extends BaseDtoMapper<Reward, RewardRequest, RewardResponse> {
    RewardMapper INSTANCE = Mappers.getMapper(RewardMapper.class);
}