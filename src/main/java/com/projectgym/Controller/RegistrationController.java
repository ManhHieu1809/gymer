package com.projectgym.Controller;

import com.projectgym.Entity.User;
import com.projectgym.dto.UserDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import com.projectgym.repository.MyAppUserRepository;

@Controller // Sử dụng @Controller thay vì @RestController để hỗ trợ redirect
@RequestMapping("/req")
public class RegistrationController {

    @Autowired
    private MyAppUserRepository myAppUserRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    // Xử lý yêu cầu từ form HTML (application/x-www-form-urlencoded) và chuyển hướng
    @PostMapping(value = "/signup", consumes = "application/x-www-form-urlencoded")
    public String createUserFromForm(
            @RequestParam String username,
            @RequestParam String password,
            @RequestParam String email) {

        if (password == null || password.isEmpty()) {
            throw new IllegalArgumentException("Password cannot be null or empty");
        }

        // Mã hóa mật khẩu
        String encodedPassword = passwordEncoder.encode(password);

        // Tạo user entity
        User user = new User();
        user.setUserName(username);
        user.setUserPassword(encodedPassword);
        user.setEmail(email);

        // Lưu vào database
        myAppUserRepository.save(user);

        // Chuyển hướng đến trang login
        return "redirect:/req/login";
    }

    // Xử lý yêu cầu từ JSON (application/json)
    @GetMapping(value = "/signup", consumes = "application/json")
    @ResponseBody // Giữ nguyên trả về JSON
    public UserDTO createUserFromJson(@RequestBody UserDTO userDTO) {
        if (userDTO.getPassword() == null || userDTO.getPassword().isEmpty()) {
            throw new IllegalArgumentException("Password cannot be null or empty");
        }

        // Mã hóa mật khẩu
        userDTO.setPassword(passwordEncoder.encode(userDTO.getPassword()));

        // Tạo user entity
        User user = new User();
        user.setUserName(userDTO.getUsername());
        user.setUserPassword(userDTO.getPassword());
        user.setEmail(userDTO.getEmail());

        // Lưu vào database
        User savedUser = myAppUserRepository.save(user);

        // Trả về thông tin đã lưu
        return new UserDTO(savedUser.getUserID(), savedUser.getUserName(), savedUser.getUserPassword(),
                savedUser.getEmail(), savedUser.getFullName(),savedUser.getAge(), savedUser.getGender(),
                savedUser.getHeight(), savedUser.getWeight(), savedUser.getRole());
    }
}
