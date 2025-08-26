package com.example.demo.service;

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
}
