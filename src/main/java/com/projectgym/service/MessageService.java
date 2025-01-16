package com.projectgym.service;

import com.projectgym.dto.MessageDTO; // Import DTO
import com.projectgym.Entity.Conversation;
import com.projectgym.Entity.Message;
import com.projectgym.Entity.User;
import com.projectgym.repository.ConversationRepository;
import com.projectgym.repository.MessageRepository;
import com.projectgym.repository.MyAppUserRepository;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
public class MessageService {

    @Autowired
    private MessageRepository messageRepository;

    @Autowired
    private MyAppUserRepository userRepository;

    @Autowired
    private ConversationRepository conversationRepository;

     /**
     * Lưu tin nhắn vào cơ sở dữ liệu.
     * @param senderUsername username của người gửi
     * @param receiverUsername username của người nhận
     * @param messageContent nội dung tin nhắn
     * @return Tin nhắn đã được lưu
     */
    @Transactional
    public MessageDTO saveMessage(String senderUsername, String receiverUsername, String messageContent, String type) {
        Optional<User> sender = userRepository.findByUserName(senderUsername);
        Optional<User> receiver = userRepository.findByUserName(receiverUsername);

        if (sender.isEmpty() || receiver.isEmpty()) {
            throw new IllegalArgumentException("Sender or Receiver not found.");
        }

        String conversationId = createConversationId(senderUsername, receiverUsername);
        Conversation conversation = conversationRepository.findByConversationId(conversationId)
                .orElseGet(() -> {
                    Conversation newConversation = new Conversation();
                    newConversation.setConversationId(conversationId);
                    newConversation.setAdminUsername(senderUsername);
                    newConversation.setCustomerUsername(receiverUsername);
                    return conversationRepository.save(newConversation);
                });


        Message message = new Message();
        message.setSenderUsername(senderUsername);
        message.setReceiverUsername(receiverUsername);
        message.setSender(sender.get());
        message.setReceiver(receiver.get());
        message.setMessage(messageContent);
        message.setType(type);
        message.setConversation(conversation);
        log.info("receiver: " + message.getReceiver());
        log.info("sender: " + message.getSender().getUserName());
        log.info("type: " + message.getType());
        Message savedMessage = messageRepository.save(message);
        log.info("Saved Message: senderUsername={}, receiverUsername={}, type={}",
                savedMessage.getSender().getUserName(), savedMessage.getReceiver().getUserName(),savedMessage.getType());



        return new MessageDTO(
                savedMessage.getId(),
                savedMessage.getSender().getUserName(),
                savedMessage.getReceiver().getUserName(),
                savedMessage.getMessage(),
                savedMessage.getType(),
                savedMessage.getConversation().getConversationId()
        );
    }



    /**
     * Lấy danh sách MessageDTO theo Conversation ID.
     * @param conversationId ID của cuộc trò chuyện
     * @return Danh sách MessageDTO
     */
    public List<MessageDTO> getMessagesByConversationId(String conversationId) {
        List<Message> messages = messageRepository.findByConversation_ConversationId(conversationId);

        // Chuyển đổi từ Message entity sang MessageDTO
        return messages.stream()
                .map(msg -> new MessageDTO(
                        msg.getId(),
                        msg.getSender().getUserName(), // Username của người gửi
                        msg.getReceiver().getUserName(), // Username của người nhận
                        msg.getMessage(), // Nội dung tin nhắn
                        msg.getType(), // Loại tin nhắn
                        msg.getConversation().getConversationId()
                ))
                .collect(Collectors.toList());
    }

    /**
     * Tạo Conversation ID từ username của người gửi và người nhận.
     * @param senderUsername username của người gửi
     * @param receiverUsername username của người nhận
     * @return Conversation ID
     */
    private String createConversationId(String senderUsername, String receiverUsername) {
        // Sắp xếp sender và receiver theo thứ tự lexicographical (để đảm bảo duy nhất)
        String[] sorted = {senderUsername, receiverUsername};
        Arrays.sort(sorted); // Sắp xếp theo thứ tự bảng chữ cái
        return sorted[0] + "-" + sorted[1]; // Tạo conversationId từ 2 tên người dùng
    }
}
