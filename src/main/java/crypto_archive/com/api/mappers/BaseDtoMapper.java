package crypto_archive.com.api.mappers;

import java.util.List;

public interface BaseDtoMapper<Entity, RequestDto, ResponseDto> {

  Entity toEntity(RequestDto requestDto);
  ResponseDto toDto(Entity entity);

  List<Entity> toEntities(List<RequestDto> requestDtos);
  List<ResponseDto> toDtos(List<Entity> entities);
}