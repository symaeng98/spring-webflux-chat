package com.example.chat.entity;

import com.example.chat.util.ChatChannel;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class ChatChannelMap {
    private final Map<String, ChatChannel> chatChannelMap = new HashMap<>();

    public ChatChannel getChannelById(String userId){
        return chatChannelMap.computeIfAbsent(userId, k -> new ChatChannel());
    }
}
