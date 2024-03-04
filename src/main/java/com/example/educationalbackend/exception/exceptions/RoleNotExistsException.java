package com.example.educationalbackend.exception.exceptions;

import lombok.Getter;

@Getter
public class RoleNotExistsException extends RuntimeException {
    private final String role;
    public RoleNotExistsException(String role) {
        super("Role " + role + " doesn't exist.");
        this.role = role;
    }
}
