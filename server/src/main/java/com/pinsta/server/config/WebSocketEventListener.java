package com.pinsta.server.config;

import org.springframework.context.event.EventListener;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;

import com.pinsta.server.chat.ChatMessage;
import com.pinsta.server.chat.MessageType;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

// this file cotains dissconeect info and handles it

@Component
@RequiredArgsConstructor
@Slf4j // for logs
public class WebSocketEventListener {

    private final SimpMessageSendingOperations messageTemplate; // Dependency Injection

    @EventListener
    public void handleWebSocketDissconnectListener(
        SessionDisconnectEvent event
    ) {
        // TODO: to be inplemented (This username has left the chat)
        StompHeaderAccessor headerAccessor = StompHeaderAccessor.wrap(event.getMessage());
        String username = (String) headerAccessor.getSessionAttributes().get("username"); // it is object so need to typecase to string
        if(username != null) {
            log.info("User dissconnected: {}", username);
            var chatMessage = ChatMessage.builder()
                .type(MessageType.LEAVE)
                .sender(username)
                .build();
                messageTemplate.convertAndSend("/topic/public", chatMessage); // it has payload
        }

    }
}
