package com.example.chat.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ChatDto {
    private String content;
    private String senderId;
    private String receiverId;
    private String senderName;
    private String receiverName;
    private String createdAt;
    private String roomId;
}
