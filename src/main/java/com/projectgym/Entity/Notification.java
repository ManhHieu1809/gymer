package com.projectgym.Entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "notification")
public class Notification {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "NotificationID")
    private int NotificationID;
    @Column(name = "content")
    private String content;

    @ManyToOne
    @JoinColumn(name = "userID",nullable = false)
    private User user;

}
