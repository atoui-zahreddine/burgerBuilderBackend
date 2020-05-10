package com.burgerbuilder.backend.DTO.Request;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Getter
@Setter
public class ResetPasswordValidationRequest {
    @Size(min = 8,message = "password length should be greater then 8.")
    @NotBlank(message = "password is required")
    private String newPassword;
    @Size(min = 36,max = 36,message = "token length should be 36.")
    @NotBlank(message = "token is required")
    private String token;
}
