package com.projectgym.repository;

import com.projectgym.Entity.NutritionPlan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NutritionPlanRepository extends JpaRepository<NutritionPlan, Long> {
    List<NutritionPlan> findByUser_UserID(Long userID); // Lấy danh sách thực đơn theo userID
}
