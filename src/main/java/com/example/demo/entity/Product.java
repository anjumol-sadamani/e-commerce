package com.example.demo.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

import jakarta.validation.constraints.*;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
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

    @ManyToOne
    @JoinColumn(name="user_id")
    private User creator;

    @JsonIgnore
    @ManyToMany(mappedBy = "products")
    private Set<Category> categories = new HashSet<>();

}
