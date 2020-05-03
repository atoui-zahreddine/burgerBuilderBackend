package com.burgerbuilder.backend.DTO.Request;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Getter
@Setter
public class SignInRequest {
    @Email(message = "invalid email address")
    @NotBlank(message = "email field is undefined or blank ")
    private String email;
    @Size(min = 8,message = "please add a password with 8+ characters")
    @NotBlank(message="password field is undefined or blank")
    private String password;
}
