package com.projectgym.repository;

import com.projectgym.Entity.Message;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MessageRepository extends JpaRepository<Message, Long> {
    List<Message> findBySender_UserIDOrReceiver_UserID(Long senderId, Long receiverId, Sort sort);
    List<Message>  findByConversation_ConversationId(String conversationId);
}
