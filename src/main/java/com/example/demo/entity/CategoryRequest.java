package com.example.demo.entity;

import lombok.Data;

import java.util.List;

@Data
public class CategoryRequest {
    private List<String> categoryNames;
}
