package com.projectgym.service;

import com.projectgym.Entity.User;
import com.projectgym.dto.CustomUserDetails;
import com.projectgym.repository.MyAppUserRepository;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CustomUserDetailsService {

    @Autowired
    private MyAppUserRepository userRepository;

    public CustomUserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // Truy vấn người dùng từ database
        Optional<User> userOptional = userRepository.findByUserName(username);

        // Kiểm tra xem người dùng có tồn tại không
        User user = userOptional.orElseThrow(() -> new UsernameNotFoundException("User not found"));

        // Trả về CustomUserDetails chứa các thông tin bổ sung
        return new CustomUserDetails(
                user.getUserID(),
                user.getUserName(),
                user.getUserPassword(),
                user.getFullName(),
                user.getEmail(),
                user.getAge(),
                user.getGender(),
                user.getWeight(),
                user.getHeight()
        );
    }
}