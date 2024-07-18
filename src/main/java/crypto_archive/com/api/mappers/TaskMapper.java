package crypto_archive.com.api.mappers;

import crypto_archive.com.api.requests.TaskRequest;
import crypto_archive.com.api.responses.TaskResponse;
import crypto_archive.com.api.table_entities.Task;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(uses = { ModelMapper.class, SavedEventMapper.class })
public interface TaskMapper extends BaseDtoMapper<Task, TaskRequest, TaskResponse> {
    TaskMapper INSTANCE = Mappers.getMapper(TaskMapper.class);
}