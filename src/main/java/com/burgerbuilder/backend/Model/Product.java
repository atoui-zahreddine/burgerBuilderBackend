package com.burgerbuilder.backend.Model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Data
@Entity
public class Product  {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Type(type="uuid-char")
    private UUID id;
    private String name;
    private Float basePrice;

    public Product(String name, Float basePrice) {
        this.name = name;
        this.basePrice = basePrice;
    }

    public Product() {
    }

    @OneToMany(mappedBy = "product",cascade = CascadeType.ALL)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIgnore
    private Set<Order> orders=new HashSet<>();

}
