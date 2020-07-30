package com.burgerbuilder.backend.Model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.UUID;

@Data
@Entity
public class Address {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Type(type="uuid-char")
    private UUID id;
    private String street;
    private String zipCode;
    private String City;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnore
    private User user;

    
}
