package com.projectgym.Controller;

import com.projectgym.dto.MessageDTO;
import com.projectgym.service.ConversationService;
import com.projectgym.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.Principal;
import java.time.LocalDateTime;
import java.util.List;

@Controller
public class ChatController {


    @Autowired
    private ConversationService conversationService;

    @Autowired
    private MessageService messageService;



    @MessageMapping("/chat/{conversationId}")
    @SendTo("/topic/messages/{conversationId}")
    public MessageDTO sendMessage(@RequestBody MessageDTO messageDTO, Principal principal) {
        if (messageDTO == null) {
            throw new IllegalArgumentException("MessageDTO is null.");
        }
        if (principal == null) {
            throw new IllegalStateException("Principal is null. User is not authenticated.");
        }

        // Lấy username từ phiên đăng nhập
        String senderUsername = principal.getName();
        System.out.println("Message received: " + messageDTO.getMessage());
        // Gọi service để lưu tin nhắn và nhận lại MessageDTO
        MessageDTO savedMessageDTO = messageService.saveMessage(senderUsername, "admin", messageDTO.getMessage(),messageDTO.getType());

        return savedMessageDTO; // Trả về DTO đã lưu để gửi lại qua WebSocket
    }


}


