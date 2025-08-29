package com.example.demo.controller;

import com.example.demo.entity.Product;
import com.example.demo.service.ProductService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @PostMapping("/product")
    public ResponseEntity<String> createProduct(@Valid @RequestBody Product product){
        productService.createProduct(product);
        return ResponseEntity.ok("Product created successfully");
    }

    @GetMapping("/product")
    public ResponseEntity<Product> getProduct(@RequestParam Long Id){
        Product product = productService.getProduct(Id);
        return ResponseEntity.ok(product);
    }
}
