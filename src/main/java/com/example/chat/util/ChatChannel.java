package com.example.chat.util;

import com.example.chat.domain.chat.dto.ChatEventDto;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Sinks.Many;

import static reactor.core.publisher.Sinks.many;

public class ChatChannel {
    private final Many<ChatEventDto> chatEvents;
    public ChatChannel(){
        this.chatEvents = many().multicast().directAllOrNothing();
    }
    public Many<ChatEventDto> getSink(){
        return this.chatEvents;
    }
    public Flux<ChatEventDto> asFlux(){
        return this.chatEvents.asFlux();
    }
}
