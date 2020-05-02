package com.burgerbuilder.backend.Model;

import lombok.Data;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Data
@Entity
public class Ingredient {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;
    private String name;
    private Float price;

    public Ingredient(String name, Float price) {
        this.name = name;
        this.price = price;
    }

    public Ingredient() {
    }

    @OneToMany(mappedBy = "ingredient",cascade = CascadeType.ALL)
    private Set<OrderedIngredients> orderedIngredients=new HashSet<>();


}
