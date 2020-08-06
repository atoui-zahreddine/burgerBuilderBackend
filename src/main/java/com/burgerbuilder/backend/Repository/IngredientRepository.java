package com.burgerbuilder.backend.Repository;

import com.burgerbuilder.backend.Model.Ingredient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Repository
public interface IngredientRepository  extends JpaRepository<Ingredient, String> {
    @Transactional
    @Modifying
    @Query("delete from Ingredient i where i.name=:ingredientName")
    void deleteByName(@Param("ingredientName") String ingredientName);

    @Transactional
    @Modifying
    @Query("update Ingredient i set i.name=:newName,i.price=:newPrice where i.name=:ingredientName")
    void updateById(@Param("ingredientName") String ingredientName,
                    @Param("newName") String newName,
                    @Param("newPrice")Float newPrice);

}
