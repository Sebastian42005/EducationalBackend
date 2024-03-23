package com.example.educationalbackend.converter;

import com.example.educationalbackend.entity.enums.UserRole;
import com.example.educationalbackend.exception.exceptions.EnumConvertException;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = true)
public class UserRoleConverter implements AttributeConverter<UserRole, String> {
    @Override
    public String convertToDatabaseColumn(UserRole userRole) {
        return switch (userRole) {
            case ADMIN -> "a";
            case STUDENT -> "s";
            case TEACHER -> "t";
        };
    }

    @Override
    public UserRole convertToEntityAttribute(String s) {
        return switch (s) {
            case "a" -> UserRole.ADMIN;
            case "s" -> UserRole.STUDENT;
            case "t" -> UserRole.TEACHER;
            default ->  throw new EnumConvertException(s + " is not known as a role");
        };
    }
}
