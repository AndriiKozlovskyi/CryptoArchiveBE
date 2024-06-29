package crypto_archive.com.api.responses;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SavedProjectResponse {
    private Integer id;
    private String name;
    private Integer project;
    private long expenses;
    private String status;
    private int amountOfAccs;
    private int orderNumber;
}
