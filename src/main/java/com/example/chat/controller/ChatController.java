package com.example.chat.controller;

import com.example.chat.dto.ChatDto;
import com.example.chat.dto.ChatEventDto;
import com.example.chat.entity.Chat;
import com.example.chat.entity.ChatChannelMap;
import com.example.chat.response.SuccessResponse;
import com.example.chat.service.ChatService;
import com.example.chat.util.ChatChannel;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
public class ChatController {
    private final ChatService chatService;
    private final ChatChannelMap chatChannelMap;

    @PostMapping("/chat")
    public void setChat(@RequestBody ChatDto chatDto){
        chatService.saveChat(chatDto);
    }

    @CrossOrigin("*")
    @GetMapping(value = "/chat/{userId}/sse", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<ChatEventDto> chatSse(@PathVariable("userId") String userId){
        System.out.println("userId : " + userId);
        ChatChannel chatChannel = chatChannelMap.getChannelById(userId);
        return chatChannel.asFlux();
    }

    @GetMapping("/chat")
    public List<ChatDto> getChatList(
            @RequestParam("userId") String userId,
            @RequestParam("receiverId") String receiverId
    ){
        System.out.println("chat 불러오기");
        return chatService.getChatList(userId, receiverId);
    }
}