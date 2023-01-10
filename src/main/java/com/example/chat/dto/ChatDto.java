package com.example.chat.dto;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

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
}
