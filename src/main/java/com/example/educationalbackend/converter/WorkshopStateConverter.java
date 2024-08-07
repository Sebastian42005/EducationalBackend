package com.example.educationalbackend.converter;

import com.example.educationalbackend.entity.enums.WorkshopState;
import com.example.educationalbackend.exception.exceptions.EnumConvertException;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = true)
public class WorkshopStateConverter implements AttributeConverter<WorkshopState, String> {
    @Override
    public String convertToDatabaseColumn(WorkshopState userRole) {
        return switch (userRole) {
            case ACCEPTED -> "a";
            case REJECTED -> "r";
            case PENDING -> "p";
        };
    }

    @Override
    public WorkshopState convertToEntityAttribute(String s) {
        return switch (s) {
            case "a" -> WorkshopState.ACCEPTED;
            case "r" -> WorkshopState.REJECTED;
            case "p" -> WorkshopState.PENDING;
            default ->  throw new EnumConvertException(s + " is not known as a role");
        };
    }
}
