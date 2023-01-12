package com.example.chat.domain.friend.service;

import com.example.chat.domain.friend.dto.FriendDto;
import com.example.chat.entity.Friend;
import com.example.chat.domain.friend.repository.FriendRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FriendService {
    private final FriendRepository friendRepository;

    public List<FriendDto> getFriendList(String userId){
        Friend friend = friendRepository.findByUserId(userId);
        List<FriendDto> friendList = friend.getFriendList();
        System.out.println(friendList.size());
        return friendList;
    }
}
