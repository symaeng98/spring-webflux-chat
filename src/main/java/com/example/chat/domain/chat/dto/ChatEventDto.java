package com.example.chat.domain.chat.dto;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

@Data
@Builder
public class ChatEventDto implements Serializable {
    private final ChatDto chatDto;
}
