package crypto_archive.com.api.requests;

import crypto_archive.com.api.table_entities.Tag;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProjectRequest {
    private String name;
    private Set<Tag> tags = new HashSet<>();
    private long expenses;
    private long participants;
    private String src;
}
