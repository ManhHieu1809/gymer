package com.projectgym.service;


import com.projectgym.dto.UserDTO;
import com.projectgym.repository.MyAppUserRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor 
public class MyAppUserService implements UserDetailsService{
    
    @Autowired
    private MyAppUserRepository repository;
    
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        
        Optional<com.projectgym.Entity.User> user = repository.findByUserName(username);
        if (user.isPresent()) {
            var userObj = user.get();
            return User.builder()
                    .username(userObj.getUserName())
                    .password(userObj.getUserPassword())
                    .build();    
        }else{
            throw new UsernameNotFoundException(username);
        }
    }
    
    
    
}
