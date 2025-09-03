package com.example.demo.service;

import com.example.demo.dto.LoginRequest;
import com.example.demo.entity.User;
import com.example.demo.exception.UnauthorizedException;
import com.example.demo.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public User authenticateUser(LoginRequest loginRequest) throws UnauthorizedException {

        User validUser = userRepository.findByNameOrEmail(loginRequest.getUsername(),loginRequest.getUsername());
        if(validUser == null){
            throw new UnauthorizedException(List.of("Invalid credentials"));
        }
        if(!passwordEncoder.matches(loginRequest.getPassword(), validUser.getPassword())){
            throw new UnauthorizedException(List.of("Invalid credentials"));
        }
        return validUser;
    }
}
