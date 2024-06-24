package crypto_archive.com.api.responses;

import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Data

@AllArgsConstructor
@NoArgsConstructor
public class ProjectResponse {
    private Integer id;
    private String name;
    private Set<String> tags = new HashSet<>();
    private long expenses;
    private long participants;
    private String imageSrc;
}
