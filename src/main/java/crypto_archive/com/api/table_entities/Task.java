package crypto_archive.com.api.table_entities;
import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "tasks")
public class Task implements TableEntity {
    @Id
    @GeneratedValue
    private Integer id;
    private String header;
    private String description;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="saved_event_id")
    @EqualsAndHashCode.Exclude
    private SavedEvent savedEvent;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="account_id")
    @EqualsAndHashCode.Exclude
    private Account account;
    private boolean completed;
}
