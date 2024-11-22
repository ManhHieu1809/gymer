package com.projectgym.service.Impl;

import com.projectgym.Entity.Notification;
import com.projectgym.Entity.User;
import com.projectgym.dto.NotificationDTO;
import com.projectgym.dto.UserDTO;
import com.projectgym.repository.NotificationRepository;
import com.projectgym.repository.OrderRepository;
import com.projectgym.service.NotificationService;
import com.projectgym.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class NotificationServiceImpl implements NotificationService {
    @Autowired
    private NotificationRepository notificationRepository;

    @Autowired
    private UserService userService;

    // Lấy thông báo của user theo userID và trả về dưới dạng NotificationDTO
    @Override
    public List<NotificationDTO> getNotificationsByUser(Long userID) {
        // Lấy tất cả thông báo của user từ repository
        List<Notification> notifications = notificationRepository.findByUser_UserID(userID);

        // Ánh xạ từ entity Notification sang DTO NotificationDTO
        return notifications.stream()
                .map(notification -> new NotificationDTO(notification)) // Chuyển mỗi Notification sang NotificationDTO
                .collect(Collectors.toList());
    }

    // Tạo một thông báo mới và trả về dưới dạng NotificationDTO
    @Override
    public NotificationDTO createNotification(NotificationDTO notificationDTO) {
        // Chuyển NotificationDTO thành entity Notification
        Notification notification = new Notification();
        notification.setContent(notificationDTO.getContent());

        // Tạo user cho thông báo từ userID
        User user = new User();
        user.setUserID(notificationDTO.getUserID()); // Set userID cho user
        notification.setUser(user); // Set user vào thông báo

        // Lưu thông báo vào database
        Notification savedNotification = notificationRepository.save(notification);

        // Chuyển lại Notification thành NotificationDTO
        return new NotificationDTO(savedNotification);
    }

    // Xóa thông báo theo notificationID
    @Override
    public void deleteNotification(Long notificationID) {
        notificationRepository.deleteById(notificationID);
    }

    // Tạo thông báo khi đơn hàng thành công và trả về NotificationDTO
    @Override
    public NotificationDTO createOrderSuccessNotification(Long userID, String orderId) {
        // Tạo thông báo mới
        Notification notification = new Notification();

        // Lấy thông tin user từ UserService
        UserDTO userDTO = userService.getUserById(userID);

        // Chỉ cần sử dụng userID của người dùng để tránh lặp dữ liệu
        User user = new User();
        user.setUserID(userDTO.getUserID()); // Chỉ lấy userID
        user.setUserName(userDTO.getUsername()); // Lấy tên người dùng, các thông tin khác có thể bỏ qua

        // Thiết lập thông tin thông báo
        notification.setUser(user); // Gắn thông báo với user
        notification.setContent("Đơn hàng #" + orderId + " đã được đặt thành công. Cảm ơn bạn đã mua sắm!");

        // Lưu thông báo vào database và trả về NotificationDTO
        Notification savedNotification = notificationRepository.save(notification);
        return new NotificationDTO(savedNotification); // Trả về NotificationDTO thay vì entity Notification
    }
}
