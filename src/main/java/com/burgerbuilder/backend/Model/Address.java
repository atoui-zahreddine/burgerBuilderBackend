package com.burgerbuilder.backend.Model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class Address {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Type(type="uuid-char")
    private UUID id;
    @NotBlank
    private String street;
    @Min(value = 1000)
    @Max(value=99999)
    private Integer zipCode;
    @NotBlank
    private String City;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnore
    private User user;

    
}
