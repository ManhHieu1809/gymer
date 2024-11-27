package com.projectgym.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class NutritionPlanDTO {
    private int nutritionID;
    private String nameNutritionPlan;
    private String descriptions;
    private double calo;
    private int userID;
}
