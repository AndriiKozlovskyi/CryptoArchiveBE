package crypto_archive.com.api.requests;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SavedProjectRequest {
    private String name;
    private long expenses;
    private String status;
    private int amountOfAccs;
}
