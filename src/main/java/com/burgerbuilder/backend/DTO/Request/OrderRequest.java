package com.burgerbuilder.backend.DTO.Request;

import com.burgerbuilder.backend.Utils.IngredientQuantityNotZero;
import lombok.Getter;
import lombok.Setter;

import java.util.HashMap;
import java.util.Map;

@Getter
@Setter
public class OrderRequest {
    @IngredientQuantityNotZero
    private Map<String,Integer> ingredients =new HashMap<>();
}