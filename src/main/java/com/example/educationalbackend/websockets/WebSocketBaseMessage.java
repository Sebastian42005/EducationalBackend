package com.example.educationalbackend.websockets;

import com.example.educationalbackend.entity.MessageEntity;

public record WebSocketBaseMessage(int workshopId, String type, MessageEntity message) {
}
