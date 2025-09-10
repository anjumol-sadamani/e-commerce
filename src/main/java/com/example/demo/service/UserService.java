package com.example.demo.service;

import com.example.demo.entity.Role;
import com.example.demo.entity.User;
import com.example.demo.exception.InvalidProductException;
import com.example.demo.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public User createUser(User user){
       return userRepository.save(user);
    }

    public User getUser(Long id){
       if (userRepository.findById(id).isEmpty()){
           throw new InvalidProductException(List.of("User did not exist"));
       } else {
           return userRepository.findById(id).get();
       }
    }

    public List<User> getAllAdminUsers(){
        List<User> users = userRepository.findAll();

        if(users.isEmpty()){
            throw new InvalidProductException(List.of("users are empty"));
        }

       return users.stream()
               .filter(user -> user.getUserRole().equals(Role.ADMIN))
               .toList();
    }
}
