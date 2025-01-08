package com.example.educationalbackend.websockets;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

@Configuration
@EnableWebSocket
public class ChatWebSocket implements WebSocketConfigurer {

    private final ChatProvider chatProvider;

    public ChatWebSocket(ChatProvider chatProvider) {
        this.chatProvider = chatProvider;
    }

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry
                .addHandler(chatProvider, "/chat")
                .setAllowedOriginPatterns("*");
    }
}
