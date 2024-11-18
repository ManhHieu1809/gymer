package com.projectgym.Entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "nutritionplan")
public class NutritionPlan {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "nutritionID")
    private int nutritionID;
    @Column(name = "name_nutrition_plan")
    private String nameNutritionPlan;
    @Column(name = "descriptions")
    private String descriptions;
    @Column(name = "calo")
    private double calo;

    @ManyToOne
    @JoinColumn(name = "userID",nullable = false)
    private User user;
}
