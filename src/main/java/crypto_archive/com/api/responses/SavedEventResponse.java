package crypto_archive.com.api.responses;

import com.fasterxml.jackson.annotation.JsonFormat;
import crypto_archive.com.api.table_entities.Task;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.lang.Nullable;

import java.time.OffsetDateTime;
import java.util.HashSet;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SavedEventResponse {
    private Integer id;
    private String name;
    private Integer event;
    private String status;
    private Set<AccountResponse> accounts = new HashSet<>();
    private Set<TaskResponse> tasks = new HashSet<>();
    private int orderNumber;
    private String link;
    private String rewardType;
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ssXXX")
    private OffsetDateTime startDate;
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ssXXX")
    private OffsetDateTime endDate;
}
