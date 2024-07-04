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
@Table(name = "tags")
public class Tag implements TableEntity {
    @Id
    @GeneratedValue
    private Integer id;
    private String name;
    @ManyToMany(mappedBy = "tags")
    @EqualsAndHashCode.Exclude
    private Set<Project> projects = new HashSet<>();
    @ManyToMany(mappedBy = "tags")
    @EqualsAndHashCode.Exclude
    private Set<Event> events = new HashSet<>();
}
