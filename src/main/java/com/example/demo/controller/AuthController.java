package com.example.demo.controller;

import com.example.demo.entity.LoginRequest;
import com.example.demo.entity.User;
import com.example.demo.exception.UnauthorizedException;
import com.example.demo.security.JwtUtil;
import com.example.demo.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class AuthController {

    private final AuthService authService;
    private final JwtUtil jwtUtil;

    @PostMapping("/login")
    public ResponseEntity<String> login(@Valid @RequestBody LoginRequest loginReq){
        try{
            User validUser = authService.authenticateUser(loginReq);
            String token = jwtUtil.createJwtToken(validUser);
            return ResponseEntity.ok(token);
        } catch (UnauthorizedException e) {
            throw new UnauthorizedException(e.getErrors());
        }
    }
}
