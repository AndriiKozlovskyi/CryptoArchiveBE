package crypto_archive.com.api.mappers;

import crypto_archive.com.api.requests.UserRequest;
import crypto_archive.com.api.responses.UserResponse;
import crypto_archive.com.api.table_entities.User;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(uses = { ModelMapper.class })
public interface UserMapper extends BaseDtoMapper<User, UserRequest, UserResponse> {
    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);
}