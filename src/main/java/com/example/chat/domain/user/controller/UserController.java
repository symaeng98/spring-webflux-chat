package com.example.chat.domain.user.controller;

import com.example.chat.domain.friend.dto.FriendDto;
import com.example.chat.domain.friend.service.FriendService;
import com.example.chat.domain.user.dto.UserDto;
import com.example.chat.domain.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class UserController {
    private final FriendService friendService;
    private final UserService userService;
    @GetMapping("/user/{userId}")
    public String index(@PathVariable("userId") String userId, Model model){
        UserDto user = userService.getUser(userId);
        List<FriendDto> friendList = friendService.getFriendList(userId);
        model.addAttribute("user", user);
        model.addAttribute("friendList",friendList);
        return "home";
    }
}
