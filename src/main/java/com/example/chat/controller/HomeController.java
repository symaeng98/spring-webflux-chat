package com.example.chat.controller;

import com.example.chat.dto.FriendDto;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.List;

@Controller
public class HomeController {
    @GetMapping("/user/{userId}")
    public String index(@PathVariable("userId") String userId, Model model){
        List<FriendDto> friendList = new ArrayList<>();
        for(int i=0;i<5;i++){
            if(String.valueOf(i).equals(userId)){
                continue;
            }
            friendList.add(
                    FriendDto.builder()
                            .id(String.valueOf(i))
                            .name("user"+ i)
                            .build()
            );
        }
        model.addAttribute("userId", userId);
        model.addAttribute("userName", "user"+userId);
        model.addAttribute("friendList",friendList);
        return "home";
    }
}
