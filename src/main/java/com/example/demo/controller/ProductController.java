package com.example.demo.controller;

import com.example.demo.dto.CategoryRequest;
import com.example.demo.dto.ProductResponse;
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

    @PostMapping("/product/{id}/categories")
    public ResponseEntity<ProductResponse> assignCategory(@PathVariable Long id, @RequestBody CategoryRequest categoryRequest){
        ProductResponse product = productService.assignCategory(id,categoryRequest);
        return ResponseEntity.ok(product);
    }

    @DeleteMapping("/product/{id}/categories/{categoryId}")
    public ResponseEntity<Product> deleteCategory(@PathVariable Long id, @PathVariable Long categoryId){
        Product product = productService.deleteCategoryFromProduct(id, categoryId);
        return ResponseEntity.ok(product);
    }
}
