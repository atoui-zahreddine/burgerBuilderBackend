package com.burgerbuilder.backend.DTO.Request;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
public class EmailValidationRequest {
    @NotBlank(message = "token is required.")
    private String token;
}
