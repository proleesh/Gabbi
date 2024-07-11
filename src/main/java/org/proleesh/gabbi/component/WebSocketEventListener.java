package org.proleesh.gabbi.component;

import lombok.RequiredArgsConstructor;
import org.proleesh.gabbi.constant.MessageType;
import org.proleesh.gabbi.entity.ChatMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionConnectedEvent;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;

/**
 * @author sung-hyuklee
 * @since JDK 21
 * date: 2024.6.27
 */
@Component
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
public class WebSocketEventListener {
    private static final Logger logger = LoggerFactory.getLogger(WebSocketEventListener.class);

    private final SimpMessageSendingOperations messagingTemplate;


    @EventListener
    public void handleWebSocketConnectListener(SessionConnectedEvent event) {
        logger.info("새로운 웹 소켓 연결을 받았습니다.");
    }

    @EventListener
    public void handleWebSocketDisconnectListener(SessionDisconnectEvent event) {
        StompHeaderAccessor headerAccessor = StompHeaderAccessor.wrap(event.getMessage());

        String username = (String)headerAccessor.getSessionAttributes().get("username");
        if(username != null){
            logger.info("끊긴 사용자: {}", username);

            ChatMessage cm = new ChatMessage();
            cm.setType(MessageType.LEAVE);
            cm.setSender(username);

            messagingTemplate.convertAndSend("/topic/chat", cm);
        }
    }
}
