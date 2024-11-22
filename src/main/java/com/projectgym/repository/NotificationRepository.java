package com.projectgym.repository;

import com.projectgym.Entity.Notification;
import com.projectgym.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface NotificationRepository extends JpaRepository<Notification, Long> {
    List<Notification> findByUser_UserID(Long userID);
}
