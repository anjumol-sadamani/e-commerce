package com.example.demo.service;

import com.example.demo.entity.Product;
import com.example.demo.exception.InvalidProductException;
import com.example.demo.repository.ProductRepository;
import com.example.demo.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final  ProductRepository productRepo;
    private final UserRepository userRepo;


    public void createProduct(Product product){

        Authentication authToken = SecurityContextHolder.getContext().getAuthentication();
        String userName = authToken.getName();
        product.setUser(userRepo.findByNameOrEmail(userName,userName));
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
