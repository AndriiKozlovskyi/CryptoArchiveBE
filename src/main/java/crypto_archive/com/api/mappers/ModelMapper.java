package crypto_archive.com.api.mappers;

import crypto_archive.com.api.table_entities.TableEntity;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper
public interface ModelMapper {
    default Integer toId(TableEntity entity) {
        return entity.getId();
    }

    List<Integer> toIds(List<TableEntity> entities);
}
