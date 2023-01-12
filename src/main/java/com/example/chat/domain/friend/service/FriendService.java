package com.example.chat.domain.friend.service;

import com.example.chat.domain.friend.dto.FriendDto;
import com.example.chat.entity.ErrorCode;
import com.example.chat.entity.Friend;
import com.example.chat.domain.friend.repository.FriendRepository;
import com.example.chat.exception.CommonException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class FriendService {
    private final FriendRepository friendRepository;

    public List<FriendDto> getFriendList(String userId){
        Friend friend = Optional.ofNullable(friendRepository.findByUserId(userId)).orElseThrow(() -> new CommonException("해당 id의 회원을 찾지 못하였습니다.", ErrorCode.INTERNAL_SERVER_ERROR));
        List<FriendDto> friendList = friend.getFriendList();
        System.out.println(friendList.size());
        return friendList;
    }
}
