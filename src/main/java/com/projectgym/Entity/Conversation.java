package com.projectgym.Entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;
@Data
@Entity
@Table(name = "conversation")
public class Conversation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "conversation_id",unique = true, nullable = false)
    private String conversationId; // Unique ID của cuộc trò chuyện

    private String adminUsername;

    private String customerUsername;

    private String avatar;

    @OneToMany(mappedBy = "conversation", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Message> messages; // Liên kết 1-nhiều với Message


}
