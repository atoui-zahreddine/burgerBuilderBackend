package com.burgerbuilder.backend.DTO.Response;

import com.burgerbuilder.backend.Model.Order;
import lombok.Getter;
import lombok.Setter;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Getter
@Setter
public class OrderResponse {
    private UUID id;
    private String status;
    private Boolean isPayed;
    private Map<String, Integer> ingredients=new HashMap<>();
    private UUID userId;
    private float totalPrice;

    public OrderResponse(Order order, UUID userId) {
        id=order.getId();
        this.userId=userId;
        totalPrice=order.getPrice();
        status=order.getStatus();
        isPayed=order.getIsPayed();
        order.getIngredients()
                .stream()
                .map(orderedIngredients -> Map.of(orderedIngredients.getIngredient().getName(), orderedIngredients.getQuantity()))
                .forEach(ingredient -> ingredients.putAll(ingredient));
    }
}
