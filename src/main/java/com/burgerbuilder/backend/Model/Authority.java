package com.burgerbuilder.backend.Model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Type;
import org.springframework.security.core.GrantedAuthority;

import javax.persistence.*;
import java.util.UUID;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Authority implements GrantedAuthority {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Type(type="uuid-char")
    private UUID id;
    private String authority;
    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

    public Authority(String authority,User user) {
        this.authority=authority;
        this.user=user;
    }

    @Override
    public String getAuthority() {
        return authority;
    }
}
