package com.burgerbuilder.backend.DTO.Request;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Getter
@Setter
@NoArgsConstructor
public class UpdatePasswordRequest {
    @NotBlank(message = "oldPassword is required.")
    private String oldPassword;
    @NotBlank(message = "newPassword is required.")
    @Size(min=8,message = "new password length should be greater then 8")
    private String newPassword;
}
