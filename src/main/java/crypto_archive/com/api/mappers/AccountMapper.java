package crypto_archive.com.api.mappers;

import crypto_archive.com.api.requests.AccountRequest;
import crypto_archive.com.api.responses.AccountResponse;
import crypto_archive.com.api.table_entities.Account;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(uses = { ModelMapper.class, DepositMapper.class, IncomeMapper.class })
public interface AccountMapper extends BaseDtoMapper<Account, AccountRequest, AccountResponse> {
    AccountMapper INSTANCE = Mappers.getMapper(AccountMapper.class);
}