package com.burgerbuilder.backend.Model;


import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import java.io.Serializable;

@Data
@Entity(name="order_ingredient")
public class OrderedIngredients implements Serializable {

    @Id
    @ManyToOne(fetch = FetchType.LAZY)
    private Order order;

    @Id
    @ManyToOne(fetch = FetchType.LAZY)
    private Ingredient ingredient;

    private Byte quantite;



}
