package com.example.demo.exception;

import java.util.List;


public record ErrorResponse(String message,List<String> errors) {
}
