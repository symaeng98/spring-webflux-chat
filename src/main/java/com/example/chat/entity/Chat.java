package com.example.chat.entity;

import com.example.chat.domain.chat.dto.ChatDto;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document("chat")
@Getter
@Setter
@Builder
public class Chat {
    @Id
    private String _id;
    private String content;
    private String senderId;
    private String receiverId;
    private String senderName;
    private String receiverName;
    private String createdAt;
    private String roomId;

    public ChatDto toDto(){
        return new ChatDto(
                content,
                senderId,
                receiverId,
                senderName,
                receiverName,
                createdAt,
                roomId
        );
    }
}
