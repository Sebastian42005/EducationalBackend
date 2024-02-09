package com.example.educationalbackend.exception;

import com.example.educationalbackend.exception.enums.EntityType;
import lombok.Getter;

@Getter
public class EntityNotFoundException extends RuntimeException {
    private int id;

    public EntityNotFoundException(EntityType entityType) {
        super("Could not find " + entityType.getName());
    }
    public EntityNotFoundException(EntityType entityType, int id) {
        super("Could not find " + entityType.getName() + " with id " + id);
        this.id = id;
    }
}
