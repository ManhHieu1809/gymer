package com.projectgym.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MessageDTO {
    private int id;
    private String senderUsername;
    private String receiverUsername;
    private String message;
    private String type;
    private String conversationId;
    // Constructor, Getters and Setters
}

