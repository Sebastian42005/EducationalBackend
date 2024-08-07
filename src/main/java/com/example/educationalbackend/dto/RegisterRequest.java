package com.example.educationalbackend.dto;

import com.example.educationalbackend.entity.UserEntity;
import com.example.educationalbackend.entity.enums.UserRole;

public record RegisterRequest (String email, String firstname, String lastname, String password, String role) {

    public UserEntity getUserEntity() {
        return UserEntity.builder()
                .email(email)
                .firstName(firstname)
                .lastName(lastname)
                .password(password)
                .role(UserRole.fromString(role))
                .build();
    }
}
