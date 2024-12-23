package com.projectgym.Controller;

import com.projectgym.Entity.Message;
import com.projectgym.dto.MessageDTO;
import com.projectgym.service.ConversationService;
import com.projectgym.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/admin/home/messages")
public class MessageController {
    @Autowired
    private MessageService messageService;

    @Autowired
    private ConversationService conversationService;

    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    /**
     * Gửi tin nhắn từ người dùng này đến người dùng khác.
     *
     * @param messageDTO Tin nhắn được gửi
     * @param principal  Để lấy thông tin người dùng hiện tại
     */
    @MessageMapping("/admin/home/messages/chat") // Nhận tin nhắn từ WebSocket
    public void sendMessage(@RequestBody MessageDTO messageDTO, Principal principal) {
        // Lấy username của người gửi từ phiên đăng nhập
        String senderUsername = principal.getName();
        messageDTO.setSenderUsername(senderUsername);

        // Lưu tin nhắn và nhận lại DTO từ service
        MessageDTO savedMessageDTO = messageService.saveMessage(
                messageDTO.getSenderUsername(),
                messageDTO.getReceiverUsername(),
                messageDTO.getMessage()
        );

        // Gửi tin nhắn tới người nhận qua WebSocket
        String receiverUsername = savedMessageDTO.getReceiverUsername();
        messagingTemplate.convertAndSendToUser(receiverUsername, "/queue/messages", savedMessageDTO);
    }


    /**
     * Lấy tất cả tin nhắn trong một cuộc trò chuyện.
     *
     * @param conversationId ID của cuộc trò chuyện
     * @return Danh sách tin nhắn
     */
    @GetMapping("/{conversationId}")
    public List<MessageDTO> getMessagesByConversationId(@PathVariable String conversationId) {
        return messageService.getMessagesByConversationId(conversationId);
    }
}
