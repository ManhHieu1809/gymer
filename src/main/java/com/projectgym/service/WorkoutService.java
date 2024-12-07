package com.projectgym.service;

import com.projectgym.dto.WorkoutDTO;

import java.util.List;

public interface WorkoutService {

    List<WorkoutDTO> getWorkoutByPlanID(Long planID);
    WorkoutDTO getWorkoutByWorkoutID(Long workoutID);
}
