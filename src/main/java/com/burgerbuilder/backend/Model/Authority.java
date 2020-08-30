package com.burgerbuilder.backend.Model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.domain.Persistable;
import org.springframework.security.core.GrantedAuthority;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Authority implements GrantedAuthority, Persistable<String> {

    @Id
    private String authority;

    @ManyToMany(mappedBy = "authorities")
    private Set<User> users=new HashSet<>();

    public Authority(String authority,User user) {
        this.authority=authority;
        users.add(user);
    }

    @Override
    public String getAuthority() {
        return authority;
    }

    @Override
    public String getId() {
        return authority;
    }

    @Override
    public boolean isNew() {
        return !authority.isEmpty();
    }
}
