package com.example.chat.domain.home.controller;

import com.example.chat.domain.friend.dto.FriendDto;
import com.example.chat.domain.friend.service.FriendService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class HomeController {
    private final FriendService friendService;
    @GetMapping("/user/{userId}")
    public String index(@PathVariable("userId") String userId, Model model){
        List<FriendDto> friendList = friendService.getFriendList(userId);
        String id = friendList.get(0).getUserId();
        String name = friendList.get(0).getName();
        System.out.println(id);
        System.out.println(name);
        model.addAttribute("userId", userId);
        model.addAttribute("userName", "user"+userId);
        model.addAttribute("friendList",friendList);
        return "home";
    }
}
