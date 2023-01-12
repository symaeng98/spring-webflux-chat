package com.example.chat.domain.chat.repository;

import com.example.chat.entity.Chat;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ChatRepository extends MongoRepository<Chat, String> {
    @Query("{$or:[{'senderId': :#{#userId},'receiverId': :#{#receiverId}}, {'senderId': :#{#receiverId},'receiverId': :#{#userId}}]}")
    List<Chat> findBySenderIdAndReceiverId(@Param("userId") String userId, @Param("receiverId") String receiverId);
}
