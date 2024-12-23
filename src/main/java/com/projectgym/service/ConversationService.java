package com.projectgym.service;

import com.projectgym.Entity.Conversation;
import com.projectgym.repository.ConversationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ConversationService {

    @Autowired
    private ConversationRepository conversationRepository;

    public Conversation createConversation(String customerUsername, String adminUsername) {
        String conversationId = adminUsername + "-" + customerUsername;

        // Kiểm tra nếu conversation đã tồn tại
        Optional<Conversation> existingConversation = conversationRepository.findByConversationId(conversationId);
        if (existingConversation.isPresent()) {
            return existingConversation.get();
        }

        // Tạo mới conversation
        Conversation conversation = new Conversation();
        conversation.setConversationId(conversationId);
        conversation.setCustomerUsername(customerUsername);
        conversation.setAdminUsername(adminUsername);

        return conversationRepository.save(conversation);
    }

    public List<String> getAllConversationIds() {
        return conversationRepository.findAll()
                .stream()
                .map(Conversation::getConversationId)
                .collect(Collectors.toList());
    }

    public Conversation getConversationByConversationId(String conversationId) {
        return conversationRepository.findByConversationId(conversationId)
                .orElse(null); // Hoặc ném ngoại lệ nếu không tìm thấy
    }
}

