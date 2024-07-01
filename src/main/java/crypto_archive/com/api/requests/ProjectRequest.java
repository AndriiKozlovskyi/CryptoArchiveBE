package crypto_archive.com.api.requests;

import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProjectRequest {
    private String name;
    private Set<Integer> tagsIds = new HashSet<>();
    private long expenses;
    private long participants;
    private String src;
}
