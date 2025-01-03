package com.projectgym.service;

import com.projectgym.dto.NutritionPlanDTO;
import com.projectgym.dto.ProductDTO;
import org.springframework.data.domain.Page;

import java.util.List;

public interface NutritionPlanService {
    NutritionPlanDTO createNutritionPlan(NutritionPlanDTO nutritionPlanDTO);

    NutritionPlanDTO updateNutritionPlan(Long nutritionID, NutritionPlanDTO nutritionPlanDTO);

    void deleteNutritionPlan(Long nutritionID);

    List<NutritionPlanDTO> getAllNutritionPlans();

    List<NutritionPlanDTO> getNutritionPlansByUser(Long userID);

    List<NutritionPlanDTO> suggestNutritionPlans(Long userID);

    NutritionPlanDTO getNutritionPlanById(Long nutritionID);

    Page<NutritionPlanDTO> getAllNutritionPlan(int page, int size);
}
