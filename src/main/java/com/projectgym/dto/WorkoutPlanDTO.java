package com.projectgym.dto;

import com.projectgym.Entity.WorkoutPlan;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class WorkoutPlanDTO {
    private int planID;
    private String planName;
    private String descriptions;
    private String duration;
    private String imageUrl;
    private int userID;
    private List<WorkoutDTO> workouts;

    public WorkoutPlanDTO(WorkoutPlan workoutPlan) {
        this.planID = workoutPlan.getPlanID();
        this.planName = workoutPlan.getPlanName();
        this.descriptions = workoutPlan.getDescriptions();

        this.workouts = workoutPlan.getWorkouts().stream().map(WorkoutDTO::new).collect(Collectors.toList());
    }
}
