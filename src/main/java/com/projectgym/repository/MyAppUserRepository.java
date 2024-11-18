package com.projectgym.repository;

import com.projectgym.Entity.User;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MyAppUserRepository extends JpaRepository<User, Long>{
    
    Optional<User> findByUserName(String username);
    
}
