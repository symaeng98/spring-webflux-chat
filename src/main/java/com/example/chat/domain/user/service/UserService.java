package com.example.chat.domain.user.service;

import com.example.chat.domain.user.dto.UserDto;
import com.example.chat.domain.user.repository.UserRepository;
import com.example.chat.entity.ErrorCode;
import com.example.chat.entity.User;
import com.example.chat.exception.CommonException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    public UserDto getUser(String userId){
        User user = userRepository.findByUserId(userId).orElseThrow(() -> new CommonException("해당 id의 회원을 찾지 못하였습니다.", ErrorCode.INTERNAL_SERVER_ERROR));
        return user.toDto();
    }
}
