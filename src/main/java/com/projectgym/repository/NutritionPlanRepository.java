package com.projectgym.repository;

import com.projectgym.Entity.NutritionPlan;
import com.projectgym.Entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NutritionPlanRepository extends JpaRepository<NutritionPlan, Long> {
    List<NutritionPlan> findByUser_UserID(Long userID);
    NutritionPlan findByNutritionID(Long nutritionID);// Lấy danh sách thực đơn theo userID
    Page<NutritionPlan> findAll(Pageable pageable);
}
