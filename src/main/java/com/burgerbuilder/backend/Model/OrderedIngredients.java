package com.burgerbuilder.backend.Model;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@Entity(name="order_ingredient")

public class OrderedIngredients implements Serializable {


    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @Type(type="uuid-char")
    private Order order;


    @ManyToOne(fetch = FetchType.LAZY)
    private Ingredient ingredient;

    private Integer quantity;

    public OrderedIngredients(Ingredient ingredient,Order order,Integer quantity ){
        this.ingredient=ingredient;
        this.order=order;
        this.quantity=quantity;
    }
}
