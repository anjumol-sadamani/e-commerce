package com.example.demo.service;

import com.example.demo.dto.ProductResponse;
import com.example.demo.entity.Category;
import com.example.demo.dto.CategoryRequest;
import com.example.demo.entity.Product;
import com.example.demo.exception.InvalidProductException;
import com.example.demo.repository.CategoryRepository;
import com.example.demo.repository.ProductRepository;
import com.example.demo.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final  ProductRepository productRepo;
    private final UserRepository userRepo;
    private final CategoryRepository categoryRepo;


    public void createProduct(Product product){

        Authentication authToken = SecurityContextHolder.getContext().getAuthentication();
        String userName = authToken.getName();
        product.setCreator(userRepo.findByNameOrEmail(userName,userName));
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

    public ProductResponse assignCategory(Long id, CategoryRequest req){

      Product product = productRepo.findById(id)
                .orElseThrow(()-> new InvalidProductException(List.of("Product not found")));

      List<String> requestedCategoryNames = req.categoryNames();
      Set<Category> categories = categoryRepo.findAllByNameIn(requestedCategoryNames)
              .orElseThrow(()-> new InvalidProductException(List.of("all the provided categories didn't exists")));

      Set<String> foundCategoryNames = categories.stream()
              .map(Category::getName)
              .collect(Collectors.toSet());

      Set<String> missing = requestedCategoryNames.stream()
              .filter(name -> !foundCategoryNames.contains(name))
              .collect(Collectors.toSet());

      if(!missing.isEmpty()){
          throw new InvalidProductException(missing.stream().toList());
      }

       product.setCategories(categories);
        productRepo.save(product);
         return fromEntity(product);
    }

    public Product deleteCategoryFromProduct(Long id, Long categoryId) {
       Product product = productRepo.findById(id)
               .orElseThrow(()-> new InvalidProductException(List.of("product didnt exists")));

       boolean wasRemoved = product
               .getCategories()
               .removeIf(category -> category.getId().equals(categoryId));

       if (!wasRemoved){
           throw new InvalidProductException(List.of("Category not found in product"));

       }
        productRepo.save(product);
        return product;
    }

    public List<Product> getAllProductsGreaterThan100Dollars(){
        List<Product> products = productRepo.findAll();

        if(products.isEmpty()){
            throw new InvalidProductException(List.of("products are empty"));
        }

        return products.stream()
                .filter(product -> {
                    int res = product.getPrice().compareTo(new BigDecimal(100));
                    return res > 0;
                })
                .toList();
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

    public static ProductResponse fromEntity(Product product) {
        NumberFormat currencyFormat = NumberFormat.getCurrencyInstance();
        String formattedPrice = currencyFormat.format(product.getPrice());

        Set<String> categoryNames = product.getCategories().stream().map(Category::getName).collect(Collectors.toSet());
        Set<String> immutableCategoryNames = Set.copyOf(categoryNames);

        return new ProductResponse(product.getName(), formattedPrice, product.getStock(),
                product.getBrand(), product.getCreator().getName(), immutableCategoryNames);
    }

}
