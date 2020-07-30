package com.burgerbuilder.backend.Utils.Validation.Constraints;



import com.burgerbuilder.backend.Utils.Validation.Validators.IngredientQuantityNotZeroValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = IngredientQuantityNotZeroValidator.class)
@Target({ElementType.FIELD, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface IngredientQuantityNotZero {
    String message() default "ingredients must contains at least one ingredient with quantity greater then 0";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
