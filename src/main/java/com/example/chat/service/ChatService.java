package com.example.chat.service;

import com.example.chat.dto.ChatDto;
import com.example.chat.dto.ChatEventDto;
import com.example.chat.entity.ChatChannelMap;
import com.example.chat.util.ChatChannel;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ChatService {
    private final ChatChannelMap chatChannelMap;

    public void saveChat(ChatDto chatDto){
        ChatChannel chatChannel = chatChannelMap.getChannelById(chatDto.getSenderId());
        chatChannel.getSink()
                .tryEmitNext(ChatEventDto.builder()
                        .chatDto(chatDto).build());
    }
}
