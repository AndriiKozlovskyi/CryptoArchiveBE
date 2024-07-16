package crypto_archive.com.api.table_entities;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.util.HashSet;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "saved_events")
public class SavedEvent implements TableEntity {
    @Id
    @GeneratedValue
    private Integer id;
    private String name;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="user_id", nullable=false)
    @EqualsAndHashCode.Exclude
    private User user;
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ssXXX")
    private OffsetDateTime startDate;
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ssXXX")
    private OffsetDateTime endDate;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="event_id")
    @EqualsAndHashCode.Exclude
    private Event event;
    private String rewardType;
    @OneToMany(fetch = FetchType.LAZY)
    @EqualsAndHashCode.Exclude
    private Set<Account> accounts = new HashSet<>();
    private String status;
    private String link;
    private int orderNumber;
}
