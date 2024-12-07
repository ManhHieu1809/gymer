package com.projectgym.service;

import com.projectgym.dto.NutritionPlanDTO;

import java.util.List;

public interface NutritionPlanService {
    NutritionPlanDTO createNutritionPlan(NutritionPlanDTO nutritionPlanDTO);

    NutritionPlanDTO updateNutritionPlan(Long nutritionID, NutritionPlanDTO nutritionPlanDTO);

    void deleteNutritionPlan(Long nutritionID);

    List<NutritionPlanDTO> getAllNutritionPlans();

    List<NutritionPlanDTO> getNutritionPlansByUser(Long userID);

    List<NutritionPlanDTO> suggestNutritionPlans(Long userID);

    NutritionPlanDTO getNutritionPlanById(Long nutritionID);
}
