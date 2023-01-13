package com.example.chat.entity;

import com.example.chat.domain.user.dto.FriendDto;
import com.example.chat.domain.user.dto.UserDto;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

@Document("user")
@Getter
@Setter
@Builder
public class User {
    @Id
    private String _id;
    private String password;
    private String name;
    private List<FriendDto> friendsList = new ArrayList<>();
    private String profileImageUrl;

    public UserDto toDto(){
        return UserDto.builder()
                .id(this._id)
                .password(this.password)
                .name(this.name)
                .friendsList(this.friendsList)
                .profileImageUrl(this.profileImageUrl)
                .build();
    }
}
