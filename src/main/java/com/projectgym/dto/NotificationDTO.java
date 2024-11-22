package com.projectgym.dto;


import com.projectgym.Entity.Notification;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class NotificationDTO {
    private int NotificationID;
    private String content;
    private int userID;

    public NotificationDTO(Notification notification) {
        this.NotificationID = notification.getNotificationID();
        this.content = notification.getContent();
        this.userID = notification.getUser().getUserID();
    }
}
