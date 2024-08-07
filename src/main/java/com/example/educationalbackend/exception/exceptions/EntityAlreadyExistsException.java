package com.example.educationalbackend.exception.exceptions;

import com.example.educationalbackend.exception.enums.EntityType;
import lombok.Getter;

@Getter
public class EntityAlreadyExistsException extends RuntimeException {
    private int id;
    private EntityType entityType;
    public EntityAlreadyExistsException(EntityType entityType, int id) {
        super(entityType + " with id " + id + " already exists");
        this.id = id;
        this.entityType = entityType;
    }
}
