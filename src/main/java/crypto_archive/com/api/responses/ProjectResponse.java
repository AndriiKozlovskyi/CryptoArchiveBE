package crypto_archive.com.api.responses;

import crypto_archive.com.api.table_entities.Tag;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Data

@AllArgsConstructor
@NoArgsConstructor
public class ProjectResponse {
    private Integer id;
    private String name;
    private Set<Integer> tags = new HashSet<>();
    private long expenses;
    private long participants;
    private String src;
    private boolean saved;
}
