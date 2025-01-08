package com.example.educationalbackend.websockets;

import com.example.educationalbackend.entity.MessageEntity;
import com.example.educationalbackend.service.WorkshopService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.WebSocketMessage;
import org.springframework.web.socket.WebSocketSession;

import java.io.IOException;
import java.time.Instant;
import java.util.ArrayList;
import java.util.concurrent.ConcurrentHashMap;

@Component
@RequiredArgsConstructor
public class ChatProvider implements WebSocketHandler {

    private final ObjectMapper objectMapper;
    private final WorkshopService workshopService;
    private final ConcurrentHashMap<Integer, ArrayList<WebSocketSession>> chatSessions = new ConcurrentHashMap<>();

    @Override
    public void afterConnectionEstablished(WebSocketSession session) {
    }

    @Override
    public void handleMessage(WebSocketSession session, WebSocketMessage<?> message) throws Exception {
        final String payload = message.getPayload().toString();

        WebSocketBaseMessage webSocketBaseMessage;

        try {
            webSocketBaseMessage = objectMapper.readValue(payload, WebSocketBaseMessage.class);
        } catch (Exception e) {
            e.printStackTrace();
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Could not be converted to " + WebSocketBaseMessage.class);
        }

        this.reactToMessageType(webSocketBaseMessage, session);
    }

    @Override
    public void handleTransportError(WebSocketSession session, Throwable exception) {

    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus closeStatus) {

    }

    @Override
    public boolean supportsPartialMessages() {
        return false;
    }

    private void reactToMessageType(WebSocketBaseMessage webSocketBaseMessage, WebSocketSession session) throws IOException {
        switch (webSocketBaseMessage.type().toLowerCase()) {
            case "new_message" -> sendMessageToWorkshop(webSocketBaseMessage.workshopId(), convertMapToObject(webSocketBaseMessage.message(), MessageEntity.class));
            case "join_chat" -> {
                ArrayList<WebSocketSession> webSockets = chatSessions.get(webSocketBaseMessage.workshopId());
                if (webSockets == null) {
                    webSockets = new ArrayList<>();
                }

                this.removeSocketFromGroup(session);

                webSockets.add(session);
                this.chatSessions.put(webSocketBaseMessage.workshopId(), webSockets);
            }
            default ->
                    throw new ResponseStatusException(HttpStatus.NOT_ACCEPTABLE, "No type found with: " + webSocketBaseMessage.type());
        }
    }

    private void removeSocketFromGroup(WebSocketSession session) {
        chatSessions.forEach((id, groupList) -> {
            if (groupList.contains(session)) {
                groupList.remove(session);
                chatSessions.replace(id, groupList);
            }
        });

    }

    public void sendMessageToWorkshop(int workshopId, MessageEntity message) {
        MessageEntity messageEntity = workshopService.sendMessage(workshopId, message);
        messageEntity.setDate(Instant.now());
        chatSessions.forEach((id, s) -> {
            if (id.equals(workshopId)) {
                this.sendToSessions(s, message);
            }
        });
    }

    /**
     * Send a websocket message to al client which are part of the array
     *
     * @param sockets list of sockets
     * @param message The message which should be sent
     */
    private void sendToSessions(ArrayList<WebSocketSession> sockets, MessageEntity message) {
        sockets.forEach(singleSession -> this.sendToSession(singleSession, message));
    }

    private void sendToSession(WebSocketSession session, MessageEntity message) {
        try {
            if (session.isOpen()) {
                session.sendMessage(new TextMessage(objectMapper.writeValueAsString(
                        message)
                ));
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Need to map it from linkedmap to class because objectmapper converts gernic objects to linkedhashmap
     */
    private <T> T convertMapToObject(Object input, Class<T> clazz) throws IOException {
        byte[] json = objectMapper.writeValueAsBytes(input);
        return objectMapper.readValue(json, clazz);
    }

}
