package com.burgerbuilder.backend.Model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@Entity
public class Ingredient {

    @Id
    private String name;
    private Float price;

    public Ingredient(String name, Float price) {
        this.name = name;
        this.price = price;
    }

    public Ingredient() {
    }

    @OneToMany(mappedBy = "ingredient",cascade = CascadeType.ALL)
    @JsonIgnore
    private Set<OrderedIngredients> orderedIngredients=new HashSet<>();


}
