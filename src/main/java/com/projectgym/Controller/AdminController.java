package com.projectgym.Controller;

import com.projectgym.Entity.User;
import com.projectgym.dto.UserDTO;
import com.projectgym.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RestController
@RequestMapping("/admin/home/users")
@Slf4j
public class AdminController {

    @Autowired
    private UserService userService;


    // Lấy danh sách tất cả tài khoản
    @GetMapping
    public ResponseEntity<List<UserDTO>> getAllUsers() {
        List<UserDTO> users = userService.getAllUsers();
        log.info("Danh sách tài khoản: {}", users);
        return ResponseEntity.ok(users);
    }


    // Lấy thông tin tài khoản theo ID
    @GetMapping("/{id}")
    public ResponseEntity<UserDTO> getUserById(@PathVariable Long id) {
        UserDTO userDTO = userService.getUserById(id);
        return ResponseEntity.ok(userDTO);
    }

    // Tạo mới tài khoản
    @PostMapping("/create")
    public ResponseEntity<User> createUser(@RequestBody User user) {
        log.info("Dữ liệu nhận được: {}", user);
        return ResponseEntity.ok(userService.createUser(user));
    }


    // Cập nhật thông tin tài khoản
    @PutMapping("/{id}")
    public ResponseEntity<UserDTO> updateUser(@PathVariable Long id, @RequestBody User user) {
        try {
            UserDTO updatedUserDTO = userService.updateUser(id, user);
            return new ResponseEntity<>(updatedUserDTO, HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }

    // Xóa tài khoản
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }

    // Tìm kiếm tài khoản theo username và fullName
    @GetMapping("/search")
    public ResponseEntity<List<UserDTO>> searchUsers(
            @RequestParam(required = false) String username,
            @RequestParam(required = false) String fullName) {
        List<UserDTO> results = userService.searchUsers(username, fullName);
        return ResponseEntity.ok(results);
    }

    // API đếm tổng số người dùng
    @GetMapping("/count")
    public ResponseEntity<Long> getUserCount() {
        long userCount = userService.countUsers();
        return new ResponseEntity<>(userCount, HttpStatus.OK);
    }
}
