package com.projectgym.service;

import com.projectgym.Entity.Conversation;
import com.projectgym.dto.ConversationDTO;
import com.projectgym.repository.ConversationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.convert.ConversionService;
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

    public List<ConversationDTO> getAllConversations() {
        return conversationRepository.findAll()
                .stream()
                .map(conversation -> new ConversationDTO(conversation.getConversationId(), conversation.getAvatar()))
                .collect(Collectors.toList());
    }


}

