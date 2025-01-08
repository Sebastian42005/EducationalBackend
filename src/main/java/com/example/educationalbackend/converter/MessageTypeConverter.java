package com.example.educationalbackend.converter;

import com.example.educationalbackend.entity.MessageEntity;
import com.example.educationalbackend.entity.enums.MessageType;
import com.example.educationalbackend.entity.enums.WorkshopState;
import com.example.educationalbackend.exception.exceptions.EnumConvertException;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = true)
public class MessageTypeConverter implements AttributeConverter<MessageType, String> {
    @Override
    public String convertToDatabaseColumn(MessageType messageType) {
        return switch (messageType) {
            case MESSAGE -> "m";
            case READ -> "r";
            case WORKSHOP_STATE_CHANGE -> "wsc";
        };
    }

    @Override
    public MessageType convertToEntityAttribute(String s) {
        return switch (s) {
            case "m" -> MessageType.MESSAGE;
            case "r" -> MessageType.READ;
            case "wsc" -> MessageType.WORKSHOP_STATE_CHANGE;
            default ->  throw new EnumConvertException(s + " is not known as a message type");
        };
    }
}
