package com.example.chat.domain.friend.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class FriendDto {
    private String userId;
    private String name;
}
