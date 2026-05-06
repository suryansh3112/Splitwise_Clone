package com.example.splitwise_clone.controller;

import com.example.splitwise_clone.dto.RegisterRequest;
import com.example.splitwise_clone.dto.UserResponse;
import com.example.splitwise_clone.entity.User;
import com.example.splitwise_clone.service.UserService;

import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public UserResponse register(@Valid @RequestBody RegisterRequest request) {
        return userService.register(request);
    }
}