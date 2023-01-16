package com.example.chat.domain.user.controller;

import com.example.chat.domain.user.dto.FriendDto;
import com.example.chat.domain.user.dto.UserDto;
import com.example.chat.domain.user.service.UserService;
import com.example.chat.domain.user.vo.FriendIdVO;
import com.example.chat.domain.user.vo.JoinVO;
import com.example.chat.domain.user.vo.LoginVO;
import com.example.chat.entity.ErrorCode;
import com.example.chat.entity.User;
import com.example.chat.exception.CommonException;
import com.example.chat.response.SuccessResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @GetMapping("/")
    public String chat(){
        return "chat";
    }
    @GetMapping("/home")
    public String index(HttpServletRequest httpServletRequest, Model model){
        String userId = (String) httpServletRequest.getSession().getAttribute("userId");
        System.out.println(userId);
        UserDto user = userService.getUserDto(userId);
        List<FriendDto> friendList = user.getFriendsList();
        model.addAttribute("user", user);
        model.addAttribute("friendList",friendList);
        return "home";
    }

    @RequestMapping("/join")
    public String join(){
        return "join";
    }

    @GetMapping("/api/join/id-checking")
    @ResponseBody
    public ResponseEntity<SuccessResponse> checkValidId(@RequestParam("userId") String userId){
        if(userService.checkId(userId)){
            throw new CommonException("이미 중복된 아이디가 존재합니다.", ErrorCode.UNAUTHORIZED_ERROR);
        }
        return ResponseEntity.ok(new SuccessResponse(true, "중복 확인 성공", null));
    }

    @PostMapping("/api/join")
    @ResponseBody
    public ResponseEntity<SuccessResponse> createMember(@RequestBody JoinVO joinVO) {
        User user = User.builder()
                ._id(joinVO.getMemberId())
                .name(joinVO.getName())
                .password(joinVO.getPassword())
                .build();
        userService.join(user);
        return ResponseEntity.ok(new SuccessResponse(true, "회원 가입을 성공였습니다.", null));
    }

    @RequestMapping("/login")
    public String home(){
        return "login";
    }

    @PostMapping("/api/member/login")
    @ResponseBody
    public ResponseEntity<SuccessResponse> loginCheck(@RequestBody LoginVO loginVO, HttpServletRequest request){
        User user = userService.login(loginVO);
        HttpSession session = request.getSession();
        session.setAttribute("userId", user.get_id());
        return ResponseEntity.ok(new SuccessResponse(true, "로그인을 성공하였습니다.", null));
    }

    @PostMapping("/api/friend")
    @ResponseBody
    public ResponseEntity<SuccessResponse> addFriend(@RequestBody FriendIdVO friendIdVO, HttpServletRequest httpServletRequest){
        String userId = (String) httpServletRequest.getSession().getAttribute("userId");
        String friendId = friendIdVO.getFriendId();
        userService.addFriend(userId, friendId);
        return ResponseEntity.ok(new SuccessResponse(true, "친구 추가를 성공하였습니다.", null));
    }
}
