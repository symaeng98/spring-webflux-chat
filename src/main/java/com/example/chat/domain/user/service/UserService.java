package com.example.chat.domain.user.service;

import com.example.chat.domain.user.dto.FriendDto;
import com.example.chat.domain.user.dto.UserDto;
import com.example.chat.domain.user.repository.UserRepository;
import com.example.chat.domain.user.vo.LoginVO;
import com.example.chat.entity.ErrorCode;
import com.example.chat.entity.User;
import com.example.chat.exception.CommonException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    public User getUser(String id){
        return userRepository.findById(id).orElseThrow(() -> new CommonException("해당 id의 회원을 찾지 못하였습니다.", ErrorCode.INTERNAL_SERVER_ERROR));
    }

    public FriendDto getFriend(String id){
        User friend = userRepository.findById(id).orElseThrow(() -> new CommonException("해당 id의 친구를 찾지 못하였습니다.", ErrorCode.INTERNAL_SERVER_ERROR));
        return FriendDto.builder()
                .id(friend.get_id())
                .name(friend.getName())
                .build();
    }

    public UserDto getUserDto(String id){
        User user = userRepository.findById(id).orElseThrow(() -> new CommonException("해당 id의 회원을 찾지 못하였습니다.", ErrorCode.INTERNAL_SERVER_ERROR));
        return user.toDto();
    }

    public void update(User user){
        userRepository.save(user);
    }

    public boolean checkId(String id){
        return userRepository.findById(id).isPresent();
    }

    public void join(User user){
        userRepository.insert(user);
    }

    public User login(LoginVO loginVO){
        return userRepository.findBy_idAndPassword(loginVO.getMemberId(), loginVO.getPassword()).orElseThrow(()->new CommonException("아이디 혹은 비밀번호를 확인하세요.", ErrorCode.UNAUTHORIZED_ERROR));
    }

    public void addFriend(String userId, String friendId){
        User user = getUser(userId);
        User friendUser = getUser(friendId);

        List<FriendDto> friendsList = user.getFriendsList();
        FriendDto friend = getFriend(friendId);
        friendsList.add(friend);
        update(user);

        List<FriendDto> friendListOfFriend = friendUser.getFriendsList();
        FriendDto userFriendDto = getFriend(userId); // id와 이름만 가진 dto
        friendListOfFriend.add(userFriendDto);
        update(friendUser);
    }
}
