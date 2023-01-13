package com.example.chat.domain.user.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class FriendDto {
    private String id;
    private String name;
}
