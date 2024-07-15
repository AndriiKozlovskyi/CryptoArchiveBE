package crypto_archive.com.api.table_entities;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.time.OffsetDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "rewards")
public class Reward implements TableEntity {
    @Id
    @GeneratedValue
    private Integer id;
    private double amount;
    private String token;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="account_id")
    @EqualsAndHashCode.Exclude
    private Account account;
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ssXXX")
    private OffsetDateTime date;
}
