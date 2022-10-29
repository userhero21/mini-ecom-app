package com.company.miniecom.domains.auth;

import lombok.*;
import org.springframework.security.core.GrantedAuthority;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Builder
public class AuthRole implements GrantedAuthority {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String code;

    @Column(nullable = false)
    private String name;

    @ManyToMany(targetEntity = AuthPermission.class, cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<AuthPermission> permissions = new ArrayList<>();

    @Override
    public String getAuthority() {
        return "ROLE_" + this.code;
    }

}
