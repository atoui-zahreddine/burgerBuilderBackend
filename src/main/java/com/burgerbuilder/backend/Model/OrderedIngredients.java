package com.burgerbuilder.backend.Model;


import com.fasterxml.jackson.annotation.JsonIgnore;
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
    @JsonIgnore
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @Type(type="uuid-char")
    @JsonIgnore
    private Order order;


    @ManyToOne(fetch = FetchType.LAZY)
//    @JsonIgnoreProperties("orderedIngredients")
    @JsonIgnore
    private Ingredient ingredient;

    private Integer quantity;

    public OrderedIngredients(Ingredient ingredient,Order order,Integer quantity ){
        this.ingredient=ingredient;
        this.order=order;
        this.quantity=quantity;
    }
}
