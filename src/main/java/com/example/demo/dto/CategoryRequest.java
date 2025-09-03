package com.example.demo.dto;

import lombok.Data;

import java.util.List;

@Data
public class CategoryRequest {
    private List<String> categoryNames;
}
