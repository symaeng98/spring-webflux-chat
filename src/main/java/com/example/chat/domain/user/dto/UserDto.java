package com.example.chat.domain.user.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserDto {
    private String userId;
    private String name;
    private String profileImageUrl;
}
