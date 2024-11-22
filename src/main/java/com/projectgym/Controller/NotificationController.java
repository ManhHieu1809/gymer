package com.projectgym.Controller;

import com.projectgym.Entity.Notification;
import com.projectgym.dto.NotificationDTO;
import com.projectgym.service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin/home/notifications")
public class NotificationController {

    @Autowired
    private NotificationService notificationService;

    // Lấy tất cả thông báo của một người dùng dựa trên userID
    @GetMapping("/user/{userID}")
    public ResponseEntity<List<NotificationDTO>> getNotificationsByUser(@PathVariable Long userID) {
        List<NotificationDTO> notifications = notificationService.getNotificationsByUser(userID);
        if (notifications.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT); // Trả về mã 204 nếu không có thông báo
        }
        return new ResponseEntity<>(notifications, HttpStatus.OK); // Trả về danh sách thông báo với mã 200
    }

    // Tạo thông báo mới (Ví dụ: thông báo đơn hàng thành công)
    @PostMapping("/order/{userID}/{orderId}")
    public ResponseEntity<NotificationDTO> createOrderSuccessNotification(
            @PathVariable Long userID,
            @PathVariable String orderId) {
        NotificationDTO notification = notificationService.createOrderSuccessNotification(userID, orderId);
        return new ResponseEntity<>(notification, HttpStatus.CREATED); // Trả về mã 201 khi tạo thông báo thành công
    }

    // Xóa thông báo theo notificationID
    @DeleteMapping("/{notificationID}")
    public ResponseEntity<Void> deleteNotification(@PathVariable Long notificationID) {
        notificationService.deleteNotification(notificationID);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT); // Trả về mã 204 khi xóa thành công
    }

    @PostMapping
    public ResponseEntity<NotificationDTO> createNotification(@RequestBody NotificationDTO notificationDTO) {
        NotificationDTO createdNotification = notificationService.createNotification(notificationDTO);
        return new ResponseEntity<>(createdNotification, HttpStatus.CREATED); // Trả về mã 201 khi tạo th��ng báo thành công
    }
}