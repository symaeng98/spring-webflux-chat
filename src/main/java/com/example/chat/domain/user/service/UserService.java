package com.example.chat.domain.user.service;

import com.example.chat.domain.user.dto.UserDto;
import com.example.chat.domain.user.repository.UserRepository;
import com.example.chat.domain.user.vo.LoginVO;
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

    public UserDto getUser(String id){
        User user = userRepository.findById(id).orElseThrow(() -> new CommonException("해당 id의 회원을 찾지 못하였습니다.", ErrorCode.INTERNAL_SERVER_ERROR));
        return user.toDto();
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
}
