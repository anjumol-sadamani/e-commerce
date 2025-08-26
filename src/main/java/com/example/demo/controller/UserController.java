package com.example.demo.controller;

import com.example.demo.entity.User;
import com.example.demo.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/")
public class UserController {

     private final UserService userService;

     @PostMapping("/user")
     public ResponseEntity<User> createUser(@RequestBody @Valid User user){
         User resp = userService.createUser(user);
         return ResponseEntity.ok(resp);
     }

     @GetMapping("/user")
     public ResponseEntity<User> getUser(@RequestParam Long id){
         User user = userService.getUser(id);
         return ResponseEntity.ok(user);
     }
}
