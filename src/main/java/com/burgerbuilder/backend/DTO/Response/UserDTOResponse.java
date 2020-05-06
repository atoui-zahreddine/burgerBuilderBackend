package com.burgerbuilder.backend.DTO.Response;

import com.burgerbuilder.backend.Model.User;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class UserDTOResponse {
    private String userId;
    private String email;
    private String name;
    private String lastName;

    public UserDTOResponse(User user) {
        this.email=user.getEmail();
        this.userId=user.getId().toString();
        this.name=user.getName();
        this.lastName=user.getLastName();
    }
}
