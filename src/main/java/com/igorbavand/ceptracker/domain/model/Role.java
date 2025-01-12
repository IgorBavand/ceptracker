package com.igorbavand.ceptracker.domain.model;

import com.igorbavand.ceptracker.enums.RoleName;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;

@Table(name = "role")
@Entity
@Data
@NoArgsConstructor
public class Role implements GrantedAuthority {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "name",unique = true, nullable = false)
    private RoleName name;

    @Override
    public String getAuthority() {
        return name.toString();
    }

    public Role(RoleName roleName) {
        this.name = roleName;
    }
}
