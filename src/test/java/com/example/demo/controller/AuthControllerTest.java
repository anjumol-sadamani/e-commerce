package com.example.demo.controller;

import com.example.demo.dto.LoginRequest;
import com.example.demo.entity.Role;
import com.example.demo.entity.User;
import com.example.demo.entity.UserProfile;
import com.example.demo.exception.UnauthorizedException;
import com.example.demo.security.JwtUtil;
import com.example.demo.service.AuthService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = AuthController.class,excludeAutoConfiguration = SecurityAutoConfiguration.class)
public class AuthControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private AuthService authService;

    @MockitoBean
    private JwtUtil jwtUtil;

    @Test
    void login() throws Exception {
        String loginRequest = """
                { "username": "testUser",
                 "password":"password"
                }
                """;

        User someUser = new User(1L,"testUser","password","email", Role.USER,new ArrayList<>(), new UserProfile());

        when(authService.authenticateUser(any(LoginRequest.class))).thenReturn(someUser);
        when(jwtUtil.createJwtToken(any(User.class))).thenReturn("fakeToken");

        mockMvc.perform(post("/api/v1/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(loginRequest))
                .andExpect(status().isOk()).andExpect(content().string("fakeToken"));
        verify(authService,times(1)).authenticateUser(any(LoginRequest.class));
        verify(jwtUtil,times(1)).createJwtToken(any(User.class));
    }

    @Test
    void loginWithUnauthorizedUser() throws Exception {
        String loginRequest = """
                { "username": "testUser",
                 "password":"password"
                }
                """;
        when(authService.authenticateUser(any(LoginRequest.class)))
                .thenThrow(new UnauthorizedException(List.of("Invalid credentials")));
        mockMvc.perform(post("/api/v1/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(loginRequest))
                .andExpect(status().isUnauthorized());

    }
}
