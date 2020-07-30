package com.burgerbuilder.backend.DTO.Request;

import com.burgerbuilder.backend.Utils.Validation.Constraints.IngredientQuantityNotZero;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import java.util.HashMap;
import java.util.Map;

@Getter
@Setter
public class OrderRequest {
    @IngredientQuantityNotZero
    @NotEmpty
    private Map<String,Integer> ingredients =new HashMap<>();
}