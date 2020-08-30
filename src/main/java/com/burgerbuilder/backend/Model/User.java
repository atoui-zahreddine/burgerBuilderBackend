package com.burgerbuilder.backend.Model;

import com.burgerbuilder.backend.DTO.Request.SignUpRequest;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Type;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.*;


@Data
@NoArgsConstructor
@Entity
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Type(type="uuid-char")
    private UUID   id;
    @NotNull
    private String email;
    @NotNull
    private String password;
    private String name;
    private String lastName;
    private String phoneNumber;
    private String emailVerificationToken;
    private boolean isEmailVerified=false;
    private boolean isPhoneNumberVerified=false;

    public User(String email,String password, String name, String lastName, String phoneNumber) {
        this.email = email;
        this.password = password;
        this.name = name;
        this.lastName = lastName;
        this.phoneNumber = phoneNumber;
        this.emailVerificationToken=UUID.randomUUID().toString();
    }

    @OneToMany(mappedBy = "user",cascade = CascadeType.ALL )
    private List<Address> addresses=new ArrayList<>();

    @ManyToMany
    @JoinTable(
            name = "user_authority",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "authority_id")
    )
    private Set<Authority> authorities=new HashSet<>();

    @OneToMany(mappedBy = "user",cascade = CascadeType.ALL)
    private List<Order> orders=new ArrayList<>();

    @OneToMany(mappedBy = "user",cascade = CascadeType.ALL)
    private List<PasswordToken> passwordTokens=new ArrayList<>();

    public User(SignUpRequest signUpRequest) {
        this.email=signUpRequest.getEmail();
        this.name=signUpRequest.getName();
        this.lastName=signUpRequest.getLastName();
        this.phoneNumber=signUpRequest.getPhoneNumber();
        this.password=signUpRequest.getPassword();
        this.emailVerificationToken=UUID.randomUUID().toString();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    public void setAuthorities(Set<Authority> authorities) {
        this.authorities = authorities;
    }

    public void addAuthority (String authority){
        this.authorities.add(new Authority(authority,this));
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
