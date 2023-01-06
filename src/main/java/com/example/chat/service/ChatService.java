package com.example.chat.service;

import com.example.chat.dto.ChatDto;
import com.example.chat.dto.ChatEventDto;
import com.example.chat.util.ChatChannel;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ChatService {
    private final ChatChannel chatChannel;

    public void saveChat(ChatDto chatDto){
        chatChannel.getSink()
                .tryEmitNext(ChatEventDto.builder()
                        .chatDto(chatDto).build());
    }
}
