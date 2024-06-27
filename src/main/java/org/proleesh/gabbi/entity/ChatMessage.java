package org.proleesh.gabbi.entity;

import lombok.Getter;
import lombok.Setter;
import org.proleesh.gabbi.constant.MessageType;
@Getter
@Setter
public class ChatMessage {
    private MessageType type;
    private String content;
    private String sender;
}
