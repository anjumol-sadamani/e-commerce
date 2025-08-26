package com.example.demo.service;

import com.example.demo.entity.Product;
import com.example.demo.exception.InvalidProductException;
import com.example.demo.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepo;

    public List<Product> findAllProducts(){
        return productRepo.findAll();
    }

    public void createProduct(Product product){

        List<String> errors = validateProduct(product);
        if(!errors.isEmpty()){
            throw new InvalidProductException(errors);
        }
        productRepo.save(product);
    }

    public Product getProduct(Long id) {
        if (productRepo.findById(id).isEmpty()){
            throw new InvalidProductException(List.of("product not found"));
        }
        else return productRepo.findById(id).get();
    }

    private List<String> validateProduct(Product product) {
        List<String> errors = new ArrayList<>();
        if(product.getStock() <0 ){
            errors.add("stock cannot be negative");
        }
        if(product.getPrice() == null || product.getPrice().compareTo(BigDecimal.ZERO) < 0){
            errors.add("price cant be negative");
        }
        return errors;
    }


}
