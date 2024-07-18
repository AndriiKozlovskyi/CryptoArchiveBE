package crypto_archive.com.api.table_entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "accounts")
public class Account implements TableEntity {
    @Id
    @GeneratedValue
    private Integer id;
    private String name;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="saved_event_id", nullable=false)
    @EqualsAndHashCode.Exclude
    private SavedEvent savedEvent;
    @OneToMany(fetch = FetchType.LAZY)
    @EqualsAndHashCode.Exclude
    private Set<Deposit> deposits = new HashSet<>();
    @OneToMany(fetch = FetchType.LAZY)
    @EqualsAndHashCode.Exclude
    private Set<Withdraw> withdraws = new HashSet<>();
    @OneToMany(fetch = FetchType.LAZY)
    @EqualsAndHashCode.Exclude
    private Set<Reward> rewards = new HashSet<>();
    @OneToMany(fetch = FetchType.LAZY)
    @EqualsAndHashCode.Exclude
    private Set<Task> tasks = new HashSet<>();
}
