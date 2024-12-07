package com.projectgym.service;

import com.projectgym.dto.WorkoutPlanDTO;

import java.util.List;

public interface WorkoutPlanService {
    WorkoutPlanDTO createWorkoutPlan(WorkoutPlanDTO workoutPlanDTO);

    WorkoutPlanDTO updateWorkoutPlan(Long planID, WorkoutPlanDTO workoutPlanDTO);

    void deleteWorkoutPlan(Long planID);

    List<WorkoutPlanDTO> getWorkoutPlansByUser(Long userID);

    WorkoutPlanDTO assignPlanToUser(Long planID, Long userID);

    WorkoutPlanDTO getWorkoutPlan(Long planID);

    List<WorkoutPlanDTO> getAllWorkoutPlans();
}
