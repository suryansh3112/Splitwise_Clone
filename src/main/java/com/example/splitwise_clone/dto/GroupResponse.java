package com.example.splitwise_clone.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class GroupResponse {
    private Long id;
    private String name;
    private String createdBy;
}