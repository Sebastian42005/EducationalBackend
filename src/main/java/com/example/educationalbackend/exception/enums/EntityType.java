package com.example.educationalbackend.exception.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum EntityType {
    SUBJECT("Subject"),
    LESSON("Lesson"),
    USER("User"),
    ADMIN("Admin"),
    TEACHER("Teacher"),
    STUDENT("Student"),
    FILE("File"),
    WORKSHOP("Workshop");

    private final String name;
}
