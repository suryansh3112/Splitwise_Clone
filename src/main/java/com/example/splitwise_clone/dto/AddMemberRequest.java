package com.example.splitwise_clone.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class AddMemberRequest {
    @Email
    @NotBlank
    private String memberEmail;
}