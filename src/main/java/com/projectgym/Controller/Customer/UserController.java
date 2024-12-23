package com.projectgym.Controller.Customer;

import com.projectgym.Entity.Conversation;
import com.projectgym.dto.*;
import com.projectgym.repository.NotificationRepository;
import com.projectgym.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/customer/home/users")
public class UserController {
    @Autowired
    private CustomUserDetailsService customUserDetailsService;

    @Autowired
    private UserService userService;

    @Autowired
    private NotificationService notificationService;

    @Autowired
    private ConversationService conversationService;

    @Autowired
    private NutritionPlanService nutritionPlanService;

    @Autowired
    private ProductService productService;

    @Autowired
    private WorkoutPlanService workoutPlanService;

    @Autowired
    private OrdersService ordersService;

    @Autowired
    private WorkoutService workoutService;

    @Autowired
    private ProgressService progressService;
    // Xem thông tin cá nhân
    @GetMapping("/{userID}")
    public UserDTO getUserProfile(@PathVariable Long userID) {
        return userService.getUserProfile(userID);
    }

    // Cập nhật thông tin cá nhân
    @PutMapping("/{userID}")
    public UserDTO updateUserProfile(@PathVariable Long userID, @RequestBody UserDTO userDTO) {
        return userService.updateUserProfile(userID, userDTO);
    }

    // Đổi mật khẩu
    @PutMapping("/{userID}/change-password")
    public String changePassword(@PathVariable Long userID,
                                 @RequestParam String oldPassword,
                                 @RequestParam String newPassword) {
        userService.changePassword(userID, oldPassword, newPassword);
        return "Password updated successfully";
    }

    // Lấy tất cả thông báo của một người dùng dựa trên userID
    @GetMapping("/notifications/{userID}")
    public ResponseEntity<List<NotificationDTO>> getNotificationsByUser(@PathVariable Long userID) {
        List<NotificationDTO> notifications = notificationService.getNotificationsByUser(userID);
        if (notifications.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT); // Trả về mã 204 nếu không có thông báo
        }
        return new ResponseEntity<>(notifications, HttpStatus.OK); // Trả về danh sách thông báo với mã 200
    }

    // Gợi ý thực đơn cho userID
    @GetMapping("/suggestions/{userID}")
    public ResponseEntity<List<NutritionPlanDTO>> suggestNutritionPlans(@PathVariable Long userID) {
        List<NutritionPlanDTO> suggestions = nutritionPlanService.suggestNutritionPlans(userID);
        return ResponseEntity.ok(suggestions);
    }



    @GetMapping("/user-info")
    public Map<String, Object> getUserInfo(Authentication authentication) {
        String username = authentication.getName(); // Lấy tên người dùng từ authentication

        // Lấy thông tin chi tiết từ CustomUserDetailsService
        CustomUserDetails customUserDetails = customUserDetailsService.loadUserByUsername(username);

        Map<String, Object> response = new HashMap<>();
        response.put("userID", customUserDetails.getUserID());
        response.put("username", customUserDetails.getUsername());
        response.put("fullName", customUserDetails.getFullName());
        response.put("email", customUserDetails.getEmail());
        response.put("age", customUserDetails.getAge());
        response.put("gender", customUserDetails.getGender());
        response.put("weight", customUserDetails.getWeight());
        response.put("height", customUserDetails.getHeight());

        return response;
    }

    @GetMapping("/user-info1")
    public ResponseEntity<Map<String, String>> getUserInfo1(Authentication authentication) {
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        String username = userDetails.getUsername();

        // Tạo một map chứa thông tin người dùng
        Map<String, String> response = new HashMap<>();
        response.put("username", username);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/conversations/create")
    public ResponseEntity<Conversation> createConversation() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String customerUsername = authentication.getName();
        String adminUsername = "admin";
        Conversation createdConversation = conversationService.createConversation(customerUsername, adminUsername);
        return ResponseEntity.ok(createdConversation);
    }

    @GetMapping("/user-info3")
    public Map<String, Object> getUserInfo3(Authentication authentication) {
        String username = authentication.getName(); // Lấy tên người dùng từ authentication

        // Lấy thông tin chi tiết từ CustomUserDetailsService
        CustomUserDetails customUserDetails = customUserDetailsService.loadUserByUsername(username);

        Map<String, Object> response = new HashMap<>();
        String userId = String.valueOf(customUserDetails.getUserID());
        response.put("userId", userId);
        return response;
    }

    // Tạo đơn hàng
    @PostMapping("/orders")
    public OrdersDTO createOrder(@RequestBody OrdersDTO ordersDTO) {
        return ordersService.createOrder(ordersDTO);
    }

    @GetMapping("/workout-plans")
    public ResponseEntity<List<WorkoutPlanDTO>> getAllWorkoutPlans() {
        List<WorkoutPlanDTO> plans = workoutPlanService.getAllWorkoutPlans();
        return new ResponseEntity<>(plans, HttpStatus.OK);
    }

    // Lấy danh sách Workout theo PlanID
    @GetMapping("/workout/{planID}")
    public ResponseEntity<List<WorkoutDTO>> getWorkoutsByPlanID(@PathVariable Long planID) {
        List<WorkoutDTO> workouts = workoutService.getWorkoutByPlanID(planID);
        return ResponseEntity.ok(workouts);
    }

    // Lấy thực đơn theo userID
    @GetMapping("/nutrition/{userID}")
    public ResponseEntity<List<NutritionPlanDTO>> getNutritionPlansByUser(@PathVariable Long userID) {
        List<NutritionPlanDTO> userPlans = nutritionPlanService.getNutritionPlansByUser(userID);
        return ResponseEntity.ok(userPlans);
    }

    @GetMapping("progress/{userID}")
    public ResponseEntity<List<ProgressDTO>> getUserProgress(@PathVariable Long userID) {
        List<ProgressDTO> progresses = progressService.getProgressByUser(userID);
        return new ResponseEntity<>(progresses, HttpStatus.OK);
    }

}
