package com.burgerbuilder.backend.Model;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.util.UUID;

@Data
@Entity
public class Ingredient {

    @Id
    private UUID id;
    private String name;
    private Float price;

    @ManyToOne
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

}
