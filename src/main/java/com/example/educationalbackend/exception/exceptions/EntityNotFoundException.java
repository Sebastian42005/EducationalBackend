package com.example.educationalbackend.exception.exceptions;

import com.example.educationalbackend.exception.enums.EntityType;
import lombok.Getter;

@Getter
public class EntityNotFoundException extends RuntimeException {
    private int id;
    private String email;
    private EntityType entityType;

    public EntityNotFoundException(EntityType entityType, String email) {
        super(entityType + " not found");
        this.entityType = entityType;
        this.email = email;
    }

    public EntityNotFoundException(EntityType entityType, int id) {
        super(entityType + " with id " + id + " not found");
        this.id = id;
        this.entityType = entityType;
    }
}
