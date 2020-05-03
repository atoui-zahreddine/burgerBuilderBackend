package com.burgerbuilder.backend.Model;

import com.burgerbuilder.backend.DTO.Request.SignUpRequest;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.UUID;


@Data
@NoArgsConstructor
@Entity
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;
    private String email;
    private String password;
    private String name;
    private String lastName;
    private String phoneNumber;

    public User(String email,String password, String name, String lastName, String phoneNumber) {
        this.email = email;
        this.password = password;
        this.name = name;
        this.lastName = lastName;
        this.phoneNumber = phoneNumber;
    }

    @OneToMany(mappedBy = "user",cascade = CascadeType.ALL )
    private List<Address> addresses=new ArrayList<>();

    @OneToMany(mappedBy = "user",cascade = CascadeType.ALL)
    private List<Order> orders=new ArrayList<>();

    public User(SignUpRequest signUpRequest) {
        this.email=signUpRequest.getEmail();
        this.name=signUpRequest.getName();
        this.lastName=signUpRequest.getLastName();
        this.phoneNumber=signUpRequest.getPhoneNumber();
        this.password=signUpRequest.getPassword();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return email;
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
