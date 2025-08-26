package com.example.demo.exception;

import lombok.Getter;

import java.util.List;

@Getter
public class InvalidProductException extends RuntimeException {
    private final List<String> errors;

    public InvalidProductException(List<String> errors) {
        super("Product validation failed");
        this.errors = errors;
    }
}

