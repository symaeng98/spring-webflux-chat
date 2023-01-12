package com.example.chat.domain.friend.repository;

import com.example.chat.entity.Friend;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FriendRepository extends MongoRepository<Friend,String> {
    Friend findByUserId(String userId);
}
