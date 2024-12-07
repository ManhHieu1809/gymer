package com.projectgym.service.Impl;

import com.projectgym.dto.WorkoutDTO;
import com.projectgym.repository.WorkoutRepository;
import com.projectgym.service.WorkoutService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class WorkoutServiceImpl implements WorkoutService {

    @Autowired
    private WorkoutRepository workoutRepository;


    @Override
    public List<WorkoutDTO> getWorkoutByPlanID(Long planID) {
        return workoutRepository.findWorkoutsByPlanID(planID);
    }

    @Override
    public WorkoutDTO getWorkoutByWorkoutID(Long workoutID) {
        return workoutRepository.findWorkoutByWorkoutID(workoutID)
                .orElseThrow(() -> new RuntimeException("Workout không tồn tại với ID: " + workoutID));
    }

}
