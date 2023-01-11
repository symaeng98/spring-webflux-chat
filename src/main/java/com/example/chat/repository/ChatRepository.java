package com.example.chat.repository;

import com.example.chat.entity.Chat;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ChatRepository extends MongoRepository<Chat, String> {
    @Query("{{'senderId' :  :#{#userId}, {'receiverId' :  :#{#receiverId}}}, {'senderId' :  :#{#receiverId}, {'receiverId' :  :#{#userId}}}")
    List<Chat> findBySenderIdAndReceiverId(@Param("userId") String userId, @Param("receiverId") String receiverId);
}
