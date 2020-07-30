package com.burgerbuilder.backend.Repository;

import com.burgerbuilder.backend.Model.OrderedIngredients;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Repository
public interface OrderedIngredientsRepository extends JpaRepository<OrderedIngredients,Long> {

    @Transactional
    @Modifying
    @Query(nativeQuery = true, value="update order_ingredient o set o.quantity=:newQuantity where o.order_id=:orderId and o.ingredient_name=:ingredientName")
    void updateQuantityByOrderIdAndIngredientName(@Param("newQuantity") int newQuantity,
                                                  @Param("orderId") String orderId,
                                                  @Param("ingredientName") String ingredientName);
}
