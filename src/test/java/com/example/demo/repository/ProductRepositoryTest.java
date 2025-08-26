package com.example.demo.repository;

import com.example.demo.entity.Product;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import static org.junit.jupiter.api.Assertions.*;

import java.math.BigDecimal;

@DataJpaTest
class ProductRepositoryTest {

    @Autowired
    private ProductRepository productRepository;

    @Test
    void testSaveProduct(){
        // Given
        Product product = new Product();
        product.setName("Laptop");
        product.setPrice(new BigDecimal("99.99"));

        //When
        Product savedProduct = productRepository.save(product);

        //Then
        assertNotNull(savedProduct.getId());
        assertEquals("Laptop", savedProduct.getName());
        assertEquals(new BigDecimal("99.99"), savedProduct.getPrice());
    }

    @Test
    void testFindByName(){
        // Given
        Product product = new Product();
        product.setName("Laptop");
        product.setPrice(new BigDecimal("999.99"));

        productRepository.save(product);

        // When
        Product foundProduct = productRepository.findByName("Laptop");

        // Then
        assertNotNull(foundProduct);
        assertEquals(product.getName(),foundProduct.getName());
        assertEquals(product.getId(), foundProduct.getId());
    }

    @Test
    void testFindByNameNotFound() {

        // When
        Product foundProduct = productRepository.findByName("NonExistentProduct");

        // Then
        assertNull(foundProduct);
    }
}
