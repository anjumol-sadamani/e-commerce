package com.example.demo.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

import java.math.BigDecimal;
import jakarta.validation.constraints.*;

@Entity
@Data
public class Product {
   @Id
   @GeneratedValue(strategy = GenerationType.AUTO)
    private Long Id;
    private String name;

    @NotNull(message = "Price must not be null")
    @PositiveOrZero(message = "Price must be zero or positive")
    private BigDecimal price;

    @NotNull(message = "Stock must not be null")
    @PositiveOrZero(message = "Stock must be zero or positive")
    private int stock;
    private String brand;

}
