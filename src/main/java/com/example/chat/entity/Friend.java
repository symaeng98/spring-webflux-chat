package com.example.chat.entity;

import com.example.chat.domain.friend.dto.FriendDto;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document("friend")
@Getter
@Setter
@Builder
public class Friend {
    @Id
    private String _id;
    private String userId;
    private List<FriendDto> friendList;
}
