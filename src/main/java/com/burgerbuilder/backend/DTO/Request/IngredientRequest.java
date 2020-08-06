package com.burgerbuilder.backend.DTO.Request;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotBlank;

@Getter
@Setter
public class IngredientRequest {
    @NotBlank
    private String name;
    @DecimalMin(value = "0.2")
    private float price;
}
