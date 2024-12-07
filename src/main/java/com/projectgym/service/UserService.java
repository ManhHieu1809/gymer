package com.projectgym.service;

import com.projectgym.Entity.User;
import com.projectgym.dto.UserDTO;

import java.util.List;

public interface UserService {
    List<UserDTO> getAllUsers();

    UserDTO getUserById(Long id);

    User createUser(User user);

    UserDTO updateUser(Long id, User user);

    void deleteUser(Long id);

    List<UserDTO> searchUsers(String username, String fullName);

    long countUsers();

}
