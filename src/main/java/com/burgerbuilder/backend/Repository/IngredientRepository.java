package com.burgerbuilder.backend.Repository;

import com.burgerbuilder.backend.Model.Ingredient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface IngredientRepository  extends JpaRepository<Ingredient, UUID> {
}
