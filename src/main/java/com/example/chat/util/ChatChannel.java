package com.example.chat.util;

import com.example.chat.dto.ChatEventDto;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Sinks;

@Component
public class ChatChannel {
    private final Sinks.Many<ChatEventDto> chatEvents;
    public ChatChannel(){
        this.chatEvents = Sinks.many().multicast().directAllOrNothing();
    }
    public Sinks.Many<ChatEventDto> getSink(){
        return this.chatEvents;
    }
    public Flux<ChatEventDto> asFlux(){
        return this.chatEvents.asFlux();
    }
}
