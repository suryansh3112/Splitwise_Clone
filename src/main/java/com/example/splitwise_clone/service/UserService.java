package com.example.splitwise_clone.service;

import com.example.splitwise_clone.dto.RegisterRequest;
import com.example.splitwise_clone.dto.UserResponse;
import com.example.splitwise_clone.entity.User;
import com.example.splitwise_clone.exception.UserAlreadyExistsException;
import com.example.splitwise_clone.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    private BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    public UserResponse register(RegisterRequest request) {

        if(userRepository.findByEmail(request.getEmail()).isPresent()) {
            throw new UserAlreadyExistsException("Email already registered");
        }

        User user = new User();

        user.setEmail(request.getEmail());
        user.setPassword(encoder.encode(request.getPassword()));

        User savedUser = userRepository.save(user);

        return new UserResponse(
                savedUser.getId(),
                savedUser.getEmail()
        );
    }
}