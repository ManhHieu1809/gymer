package com.projectgym.service;

import com.projectgym.Entity.Notification;
import com.projectgym.dto.NotificationDTO;

import java.util.List;

public interface NotificationService {
    List<NotificationDTO> getNotificationsByUser(Long userID);
    NotificationDTO createNotification(NotificationDTO notificationDTO);
    void deleteNotification(Long notificationID);
    NotificationDTO createOrderSuccessNotification(Long userID, String orderId);
}
