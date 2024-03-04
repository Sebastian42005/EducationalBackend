package com.example.educationalbackend.entity.enums;


import lombok.Getter;

import java.util.Arrays;

@Getter
public enum UserRole {
    TEACHER("Teacher"),
    STUDENT("Student"),
    ADMIN("Admin");

    private final String name;

    UserRole(String name) {
        this.name = name;
    }

    public static UserRole fromString(String name) {
        return Arrays.stream(UserRole.values()).filter(role -> role.name.equalsIgnoreCase(name))
                .findFirst().orElseThrow();
    }
}
