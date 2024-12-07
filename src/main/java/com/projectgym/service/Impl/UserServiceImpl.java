package com.projectgym.service.Impl;

import com.projectgym.Entity.User;
import com.projectgym.dto.UserDTO;
import com.projectgym.repository.MyAppUserRepository;
import com.projectgym.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private MyAppUserRepository repository;

    private PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Override
    public List<UserDTO> getAllUsers() {
        List<User> users = repository.findAll();
        return users.stream()
                .map(user -> new UserDTO(user.getUserID(), user.getUserName(),null, user.getEmail(),
                        user.getFullName(), user.getAge(), user.getGender(), user.getHeight(),
                        user.getWeight(),  user.getRole() != null ? UserDTO.Role.valueOf(user.getRole().name()) : null))
                .collect(Collectors.toList());
    }

    @Override
    public UserDTO getUserById(Long id) {
        User user = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + id));

        // Ánh xạ từ User sang UserDTO
        return new UserDTO(
                user.getUserID(),
                user.getUserName(),
                null, // Không trả về password để bảo mật
                user.getEmail(),
                user.getFullName(),
                user.getAge(),
                user.getGender(),
                user.getHeight(),
                user.getWeight(),
                user.getRole() != null ? UserDTO.Role.valueOf(user.getRole().name()) : null
        );
    }

    @Override
    public User createUser(User user) {
        if (user.getUserPassword() != null && !user.getUserPassword().isEmpty()) {
            String encodedPassword = passwordEncoder.encode(user.getUserPassword());
            user.setUserPassword(encodedPassword);  // Cập nhật mật khẩu đã mã hóa vào user
        }
        return repository.save(user);
    }

    @Override
    public UserDTO updateUser(Long id, User user) {
        User existingUser = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + id));

        if (user.getUserPassword() != null && !user.getUserPassword().isEmpty()) {
            String encodedPassword = passwordEncoder.encode(user.getUserPassword());
            existingUser.setUserPassword(encodedPassword);  // Cập nhật mật khẩu đã mã hóa
        }

        // Cập nhật các trường
        existingUser.setUserName(user.getUserName());
        existingUser.setFullName(user.getFullName());
        existingUser.setAge(user.getAge());
        existingUser.setGender(user.getGender());
        existingUser.setHeight(user.getHeight());
        existingUser.setWeight(user.getWeight());
        existingUser.setRole(user.getRole()); // Cập nhật vai trò nếu cần

        // Lưu thông tin người dùng đã cập nhật
        User updatedUser = repository.save(existingUser);

        return new UserDTO(
                updatedUser.getUserID(),
                updatedUser.getUserName(),
                null, // Không trả về mật khẩu
                updatedUser.getEmail(),
                updatedUser.getFullName(),
                updatedUser.getAge(),
                updatedUser.getGender(),
                updatedUser.getHeight(),
                updatedUser.getWeight(),
                updatedUser.getRole() != null ? UserDTO.Role.valueOf(updatedUser.getRole().name()) : null
        );
    }

    @Override
    public void deleteUser(Long id) {
        repository.deleteById(id);
    }

    @Override
    public List<UserDTO> searchUsers(String username, String fullName) {
        List<User> users = repository.searchByUsernameOrFullName(username, fullName);
        return users.stream()
                .map(user -> new UserDTO(
                        user.getUserID(),
                        user.getUserName(),
                        null,
                        user.getEmail(),
                        user.getFullName(),
                        user.getAge(),
                        user.getGender(),
                        user.getHeight(),
                        user.getWeight(),
                        user.getRole() != null ? UserDTO.Role.valueOf(user.getRole().name()) : null
                ))
                .collect(Collectors.toList());
    }

    @Override
    public long countUsers() {
        return repository.count();  // Spring Data JPA sẽ tự động cung cấp phương thức count()
    }

}
