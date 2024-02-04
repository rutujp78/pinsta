package com.pinsta.server.chat;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.stereotype.Controller;

@Controller
public class ChatController {
    
    @MessageMapping("/chat.sendMessage") // url that invoked sendMessage and it is endpoint
    @SendTo("/topic/public") // Where to send this message automatically since we are using @Payload
    public ChatMessage sendMessage(
        
        @Payload ChatMessage chatMessage
    ) {
        // lets create chat message object
        return chatMessage;
    }

    @MessageMapping("/chat.addUser")
    @SendTo("/topic/public")
    public ChatMessage addUser(
        @Payload ChatMessage chatMessage,
        SimpMessageHeaderAccessor headerAccessor
    ) {
        // Add username in websocket session
        headerAccessor.getSessionAttributes().put("username", chatMessage.getSender());
        return chatMessage;
    }

}
