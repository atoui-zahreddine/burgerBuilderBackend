package com.burgerbuilder.backend.DTO.Request;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
public class ResetPasswordRequest {
    @NotBlank(message = "user email is required .")
    private String email;
}
