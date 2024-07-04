package crypto_archive.com.api.table_entities;

import crypto_archive.com.api.enums.Role;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.lang.Nullable;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Getter
@Table(name = "users")
public class User implements UserDetails, TableEntity  {
    @Id
    @GeneratedValue
    private Integer id;
    private String email;
    private String username;
    private String password;
    @Enumerated(EnumType.STRING)
    private Role role;
    @ManyToMany(mappedBy = "participants")
    private Set<Event> events = new HashSet<>();

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority((role.name())));
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
