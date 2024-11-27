package com.projectgym.repository;

import com.projectgym.Entity.Progress;
import com.projectgym.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProgressRepository extends JpaRepository<Progress, Long> {

    List<Progress> findByUser(User userID);

    List<Progress> findByUserIn(List<User> users); // Tìm kiếm tiến độ của người dùng trong danh sách

    @Query("SELECT p FROM Progress p WHERE p.user.fullName LIKE %:userName%")
    List<Progress> findByUserName(@Param("userName") String userName);
}
