package com.example.chat.domain.chat.service;

import com.example.chat.domain.chat.dto.ChatDto;
import com.example.chat.domain.chat.dto.ChatEventDto;
import com.example.chat.entity.Chat;
import com.example.chat.entity.ChatChannelMap;
import com.example.chat.entity.ErrorCode;
import com.example.chat.exception.CommonException;
import com.example.chat.domain.chat.repository.ChatRepository;
import com.example.chat.util.ChatChannel;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ChatService {
    private final ChatChannelMap chatChannelMap;
    private final ChatRepository chatRepository;

    public void saveChat(ChatDto chatDto){
        ChatChannel chatChannel = chatChannelMap.getChannelById(chatDto.getReceiverId());
        chatChannel.getSink()
                .tryEmitNext(ChatEventDto.builder()
                        .chatDto(chatDto).build());
        System.out.println(chatDto.getSenderId());
        insertChat(chatDto.toEntity());
    }

    public Chat getChat(String _id){
        return chatRepository.findById(_id).orElseThrow(()->new RuntimeException("채팅이 없음"));
    }
    public void insertChat(Chat chat){
        chatRepository.insert(chat);
    }

    public List<ChatDto> getChatList(String userId, String receiverId){
        List<ChatDto> chatDtoList = new ArrayList<>();
        try {
            List<Chat> chatList = chatRepository.findBySenderIdAndReceiverId(userId, receiverId);
            for (Chat chat : chatList) {
                chatDtoList.add(chat.toDto());
            }
            return chatDtoList;
        }catch (Exception e){
            throw new CommonException("채팅 불러오기 실패", ErrorCode.INTERNAL_SERVER_ERROR);
        }
    }
}
