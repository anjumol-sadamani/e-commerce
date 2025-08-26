package com.example.demo.service;

import com.example.demo.entity.Product;
import com.example.demo.exception.InvalidProductException;
import com.example.demo.repository.ProductRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class ProductServiceTest {

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private ProductService productService;

    @Test
     void testCreateProduct(){
        //Given
        Product product = new Product();
        product.setPrice(BigDecimal.valueOf(8.0));
        //When
        productService.createProduct(product);
        //Then
        verify(productRepository, times(1)).save(any(Product.class));
    }

    @ParameterizedTest
    @ValueSource(doubles = {-1.0, -100.0, -0.01})
    void testInvalidPrice(double price){
        Product product = new Product();
        product.setPrice(BigDecimal.valueOf(price));
        assertThrows(InvalidProductException.class, ()->{
            productService.createProduct(product);
        });
        verify(productRepository, times(0)).save(any(Product.class));

    }

    @ParameterizedTest
    @ValueSource(ints = {-1, -100, -21})
    void testInvalidStock(int stock){
        Product product = new Product();
        product.setStock(stock);
        assertThrows(InvalidProductException.class, ()->{
            productService.createProduct(product);
        });
        verify(productRepository, times(0)).save(any(Product.class));
    }
}
