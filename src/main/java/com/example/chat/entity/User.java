package com.example.chat.entity;

import com.example.chat.domain.user.dto.UserDto;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document("user")
@Getter
@Setter
@Builder
public class User {
    @Id
    private String _id;
    private String userId;
    private String name;
    private String profileImageUrl;

    public UserDto toDto(){
        return UserDto.builder()
                .userId(this.userId)
                .name(this.name)
                .profileImageUrl(this.profileImageUrl)
                .build();
    }
}
