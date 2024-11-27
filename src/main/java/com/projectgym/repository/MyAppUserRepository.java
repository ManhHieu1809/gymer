package com.projectgym.repository;

import com.projectgym.Entity.User;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MyAppUserRepository extends JpaRepository<User, Long>{
    
    Optional<User> findByUserName(String username);
    List<User> findByFullNameContaining(String fullName);


    @Query("SELECT u FROM User u " +
            "WHERE (:username IS NULL OR LOWER(u.userName) LIKE LOWER(CONCAT('%', :username, '%'))) " +
            "AND (:fullName IS NULL OR LOWER(u.fullName) LIKE LOWER(CONCAT('%', :fullName, '%')))")
    List<User> searchByUsernameOrFullName(@Param("username") String username, @Param("fullName") String fullName);
}
