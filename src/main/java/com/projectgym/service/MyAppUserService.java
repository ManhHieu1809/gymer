package com.projectgym.service;


import com.projectgym.dto.UserDTO;
import com.projectgym.repository.MyAppUserRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Optional;

@Service
@AllArgsConstructor 
public class MyAppUserService implements UserDetailsService{
    
    @Autowired
    private MyAppUserRepository repository;
    
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        Optional<com.projectgym.Entity.User> userOptional = repository.findByUserName(username);

        // Nếu không tìm thấy, ném ngoại lệ
        if (userOptional.isEmpty()) {
            throw new UsernameNotFoundException("User not found: " + username);
        }

        // Nếu tìm thấy, xử lý thông tin user
        com.projectgym.Entity.User user = userOptional.get();
        GrantedAuthority authority = new SimpleGrantedAuthority("ROLE_" + user.getRole().name().toUpperCase());

        return new org.springframework.security.core.userdetails.User(
                user.getUserName(),
                user.getUserPassword(),
                Collections.singletonList(authority)
        );
    }
    
}
