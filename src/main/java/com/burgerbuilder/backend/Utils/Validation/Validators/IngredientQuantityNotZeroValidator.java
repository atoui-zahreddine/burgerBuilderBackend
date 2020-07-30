package com.burgerbuilder.backend.Utils.Validation.Validators;

import com.burgerbuilder.backend.Utils.Validation.Constraints.IngredientQuantityNotZero;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Map;

public class IngredientQuantityNotZeroValidator implements ConstraintValidator<IngredientQuantityNotZero, Map<String,Integer>> {

    @Override
    public boolean isValid(Map<String, Integer> ingredients, ConstraintValidatorContext constraintValidatorContext) {
        return ingredients.entrySet().stream().allMatch( ingredient -> ingredient.getValue()>0 );
    }
}
