package com.ecommerce.ecommerce.models;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.*;
import org.springframework.validation.annotation.Validated;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@Entity
@Validated
public class Address {
    @Id
    @GeneratedValue
    private Integer id;
    private String street;
    private String houseNumber;
    private String zipCode;
}
