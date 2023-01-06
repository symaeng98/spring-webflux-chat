package com.example.chat.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ChatDto {
    private String content;
    private int senderId;
    private int receiverId;
    private String senderName;
    private String receiverName;
    private String createdAt;
}
