package com.burgerbuilder.backend.Model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.domain.Persistable;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@Entity
public class Ingredient implements Persistable<String> {

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


    @Override
    public String getId() {
        return name;
    }

    @Override
    public boolean isNew() {
        return name!=null;
    }
}
