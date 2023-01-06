package com.example.chat.controller;

import com.example.chat.dto.ChatDto;
import com.example.chat.dto.ChatEventDto;
import com.example.chat.service.ChatService;
import com.example.chat.util.ChatChannel;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;

@RestController
@RequiredArgsConstructor
public class ChatController {
    private final ChatService chatService;
    private final ChatChannel chatChannel;

    @PostMapping("/chat")
    public void setChat(@RequestBody ChatDto chatDto){
        System.out.println(chatDto.getContent());
        System.out.println(chatDto.getSenderId());
        chatService.saveChat(chatDto);
    }

    @CrossOrigin("*")
    @GetMapping(value = "/chat/sse", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<ChatEventDto> chatSse(){
        return chatChannel.asFlux();
    }
}
