package crypto_archive.com.api.responses;

import com.fasterxml.jackson.annotation.JsonFormat;
import crypto_archive.com.api.table_entities.Task;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.util.HashSet;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EventResponse {
    private Integer id;
    private String name;
    private Set<TagResponse> tags = new HashSet<>();
    private Set<UserResponse> participants = new HashSet<>();
    private String src;
    private Set<TaskResponse> tasks;
    private boolean saved;
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ssXXX")
    private OffsetDateTime startDate;
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ssXXX")
    private OffsetDateTime endDate;
}
