package com.burgerbuilder.backend.Model;

import lombok.Data;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.util.*;

@Data
@Entity
public class Product {
    @Id
    private UUID id;
    private String name;
    private Float basePrice;

    @OneToMany(mappedBy = "product",cascade = CascadeType.ALL)
    private List<Ingredient> ingredients=new ArrayList<>();

    @ManyToMany(mappedBy = "products",cascade = {CascadeType.MERGE,CascadeType.PERSIST})
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Set<Order> orders=new HashSet<>();
}
