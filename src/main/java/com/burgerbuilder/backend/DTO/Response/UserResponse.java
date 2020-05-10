package com.burgerbuilder.backend.DTO.Response;

import com.burgerbuilder.backend.Model.User;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@NoArgsConstructor
public class UserResponse {
    private String userId;
    private String email;
    private String name;
    private String lastName;
    private List<String> authority=new ArrayList<>();

    public UserResponse(User user) {
        this.email=user.getEmail();
        this.userId=user.getId().toString();
        this.name=user.getName();
        this.lastName=user.getLastName();
        this.authority=user.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList());
    }
}
