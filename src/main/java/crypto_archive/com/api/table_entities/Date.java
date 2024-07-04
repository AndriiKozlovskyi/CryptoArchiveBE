package crypto_archive.com.api.table_entities;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.util.HashSet;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "dates")
public class Date {
    @Id
    @GeneratedValue
    private Integer id;
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ssXXX")
    private OffsetDateTime date;
    @ManyToMany
    @JoinTable(
            name = "date_saved_event",
            joinColumns = @JoinColumn(name = "date_id"),
            inverseJoinColumns = @JoinColumn(name = "event_id")
    )
    private Set<SavedEvent> events = new HashSet<>();

}
