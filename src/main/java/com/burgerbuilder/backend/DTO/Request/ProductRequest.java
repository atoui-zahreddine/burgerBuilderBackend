package com.burgerbuilder.backend.DTO.Request;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

@Getter
@Setter
@NoArgsConstructor
public class ProductRequest {
    @NotBlank
    private String name;

    @Min(value = 1,message = "must be greater then 1.")
    @Max(value = 5,message = "must be less then 5.")
    private float basePrice;
}
