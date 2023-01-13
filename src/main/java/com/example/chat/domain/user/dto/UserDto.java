package com.example.chat.domain.user.dto;

import lombok.Builder;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
@Builder
public class UserDto {
    private String id;
    private String password;
    private String name;
    private List<FriendDto> friendsList;
    private String profileImageUrl;
}
