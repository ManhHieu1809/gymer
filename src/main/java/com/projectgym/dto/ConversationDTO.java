package com.projectgym.dto;


import lombok.Data;


@Data
public class ConversationDTO {
    private String ConversationID;
    private String avatar;


    public ConversationDTO(String conversationId, String avatar) {
        ConversationID = conversationId;
        this.avatar = avatar;
    }
}
