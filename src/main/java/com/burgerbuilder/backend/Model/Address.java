package com.burgerbuilder.backend.Model;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.util.UUID;

@Data
@Entity
public class Address {

    @Id
    private UUID id;
    private String street;
    private String zipCode;
    private String City;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    
}
