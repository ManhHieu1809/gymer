package com.projectgym.Controller;

import com.projectgym.Entity.User;
import com.projectgym.dto.UserDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.projectgym.repository.MyAppUserRepository;

@RestController
public class RegistrationController {

    @Autowired
    private MyAppUserRepository myAppUserRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;


    @PostMapping(value = "/req/signup", consumes = "application/json")
    public UserDTO createUser(@RequestBody UserDTO userDTO) {
        if (userDTO.getPassword() == null) {
            throw new IllegalArgumentException("Password cannot be null");
        }

        userDTO.setPassword(passwordEncoder.encode(userDTO.getPassword()));

        User user = new User();
        user.setUserName(userDTO.getUsername());
        user.setUserPassword(userDTO.getPassword());
        user.setEmail(userDTO.getEmail());

        User savedUser = myAppUserRepository.save(user);
        return new UserDTO(savedUser.getUserName(), savedUser.getUserPassword(), savedUser.getEmail());
    }
}

