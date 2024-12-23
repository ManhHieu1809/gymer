package com.projectgym.repository;

import com.projectgym.Entity.Conversation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ConversationRepository extends JpaRepository<Conversation, Integer> {

    List<Conversation> findAllByAdminUsername(String adminUsername); // Lấy danh sách conversation theo admin

    Optional<Conversation> findByConversationId(String conversationId);
}
