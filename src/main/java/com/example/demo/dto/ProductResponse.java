package com.example.demo.dto;

import java.util.Set;

public record ProductResponse(String name, String price, int stock, String brand,
                              String username, Set<String> categories){}
