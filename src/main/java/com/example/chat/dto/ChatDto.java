package com.example.chat.dto;

import com.example.chat.entity.Chat;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

@Data
@NoArgsConstructor
public class ChatDto {
    private String content;
    private String senderId;
    private String receiverId;
    private String senderName;
    private String receiverName;
    private String createdAt;
    private String roomId;

    public ChatDto(String content, String senderId, String receiverId, String senderName, String receiverName, String createdAt, String roomId) {
        this.content = content;
        this.senderId = senderId;
        this.receiverId = receiverId;
        this.senderName = senderName;
        this.receiverName = receiverName;
        this.createdAt = createdAt;
        this.roomId = roomId;
    }

    public Chat toEntity(){
        return Chat.builder()
                .content(this.content)
                .senderId(this.senderId)
                .receiverId(this.receiverId)
                .senderName(this.senderName)
                .receiverName(this.receiverName)
                .createdAt(this.createdAt)
                .build();
    }
}
