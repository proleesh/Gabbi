package org.proleesh.gabbi.controller;

import org.proleesh.gabbi.entity.ChatMessage;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
/**
 * @author sung-hyuklee
 * date: 2024.6.27 ~ 6.28
 * Chat Controller
 */
@Controller
public class ChatController {
    @GetMapping("/chat")
    public String chatStart() {
        return "chat/chat";
    }



    @MessageMapping("/chat.sendMessage")
    @SendTo("/topic/public")
    public ChatMessage sendMessage(@Payload ChatMessage message) {
        return message;
    }

    @MessageMapping("/chat.addUser")
    @SendTo("/topic/public")
    public ChatMessage addUser(@Payload ChatMessage message,
                               SimpMessageHeaderAccessor headerAccessor) {
        headerAccessor.getSessionAttributes()
                .put("username", message.getSender());
        return message;
    }

}
