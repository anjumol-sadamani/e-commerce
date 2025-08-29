package com.example.demo.exception;

import lombok.Getter;

import java.util.List;

@Getter
public class UnauthorizedException extends RuntimeException{
    private final List<String> errors;

    public UnauthorizedException(List<String> errors) {
        super("Unauthorized access");
        this.errors = errors;
    }
}

