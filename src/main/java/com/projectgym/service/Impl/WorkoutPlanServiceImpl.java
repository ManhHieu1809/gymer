package com.projectgym.service.Impl;

import com.projectgym.Entity.User;
import com.projectgym.Entity.WorkoutPlan;
import com.projectgym.dto.WorkoutDTO;
import com.projectgym.dto.WorkoutPlanDTO;
import com.projectgym.repository.MyAppUserRepository;
import com.projectgym.repository.WorkoutPlanRepository;
import com.projectgym.service.WorkoutPlanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
@Service
public class WorkoutPlanServiceImpl implements WorkoutPlanService {
    @Autowired
    private WorkoutPlanRepository workoutPlanRepository;

    @Autowired
    private MyAppUserRepository userRepository;

   @Override
public WorkoutPlanDTO createWorkoutPlan(WorkoutPlanDTO workoutPlanDTO) {
    WorkoutPlan plan = new WorkoutPlan();
    plan.setPlanName(workoutPlanDTO.getPlanName());
    plan.setDescriptions(workoutPlanDTO.getDescriptions());
    plan.setDuration(workoutPlanDTO.getDuration());

    // Gán kế hoạch nếu có userID
    if (workoutPlanDTO.getUserID() != 0) {
        User user = userRepository.findById((long) workoutPlanDTO.getUserID())
                .orElseThrow(() -> new RuntimeException("User not found"));
        plan.setUser(user);
    }

    WorkoutPlan savedPlan = workoutPlanRepository.save(plan);

    List<WorkoutDTO> workoutDTOs = savedPlan.getWorkouts().stream()
            .map(workout -> new WorkoutDTO(workout.getWorkoutID(),workout.getWorkoutName(), workout.getCategory(), workout.getInstructions(),workout.getImageUrl(),workout.getVideoUrl()))
            .collect(Collectors.toList());

    return new WorkoutPlanDTO(
            savedPlan.getPlanID(),
            savedPlan.getPlanName(),
            savedPlan.getDescriptions(),
            savedPlan.getDuration(),
            savedPlan.getImageUrl(),
            savedPlan.getUser() != null ? savedPlan.getUser().getUserID() : 0,
            workoutDTOs
    );
}

    @Override
    public WorkoutPlanDTO updateWorkoutPlan(Long planID, WorkoutPlanDTO workoutPlanDTO) {
        WorkoutPlan plan = workoutPlanRepository.findById(planID)
                .orElseThrow(() -> new RuntimeException("Plan not found"));

        plan.setPlanName(workoutPlanDTO.getPlanName());
        plan.setDescriptions(workoutPlanDTO.getDescriptions());
        plan.setDuration(workoutPlanDTO.getDuration());

        WorkoutPlan updatedPlan = workoutPlanRepository.save(plan);

        List<WorkoutDTO> workoutDTOs = updatedPlan.getWorkouts().stream()
                .map(workout -> new WorkoutDTO(workout.getWorkoutID(),workout.getWorkoutName(), workout.getCategory(), workout.getInstructions(),workout.getImageUrl(),workout.getVideoUrl()))
                .collect(Collectors.toList());

        return new WorkoutPlanDTO(
                updatedPlan.getPlanID(),
                updatedPlan.getPlanName(),
                updatedPlan.getDescriptions(),
                updatedPlan.getDuration(),
                updatedPlan.getImageUrl(),
                updatedPlan.getUser() != null ? updatedPlan.getUser().getUserID() : null,
                workoutDTOs

        );
    }

    @Override
    public void deleteWorkoutPlan(Long planID) {
        if (!workoutPlanRepository.existsById(planID)) {
            throw new RuntimeException("Plan not found");
        }
        workoutPlanRepository.deleteById(planID);
    }

    @Override
    public List<WorkoutPlanDTO> getWorkoutPlansByUser(Long userID) {
        return workoutPlanRepository.findByUserUserID(userID).stream()
                .map(plan -> new WorkoutPlanDTO(
                        plan.getPlanID(),
                        plan.getPlanName(),
                        plan.getDescriptions(),
                        plan.getDuration(),
                        plan.getImageUrl(),
                        plan.getUser().getUserID(),
                        plan.getWorkouts().stream()
                                .map(workout -> new WorkoutDTO(workout.getWorkoutID(),workout.getWorkoutName(), workout.getCategory(), workout.getInstructions(), workout.getImageUrl(), workout.getVideoUrl()))
                                .collect(Collectors.toList())
                ))
                .collect(Collectors.toList());
    }

    @Override
    public WorkoutPlanDTO assignPlanToUser(Long planID, Long userID) {
        WorkoutPlan plan = workoutPlanRepository.findById(planID)
                .orElseThrow(() -> new RuntimeException("Plan not found"));

        User user = userRepository.findById(userID)
                .orElseThrow(() -> new RuntimeException("User not found"));

        plan.setUser(user);
        WorkoutPlan updatedPlan = workoutPlanRepository.save(plan);
        List<WorkoutDTO> workoutDTOS = updatedPlan.getWorkouts().stream()
                .map(workout -> new WorkoutDTO(workout.getWorkoutID(),workout.getWorkoutName(), workout.getCategory(), workout.getInstructions(),workout.getImageUrl(),workout.getVideoUrl()))
                .collect(Collectors.toList());
        return new WorkoutPlanDTO(
                updatedPlan.getPlanID(),
                updatedPlan.getPlanName(),
                updatedPlan.getDescriptions(),
                updatedPlan.getDuration(),
                updatedPlan.getImageUrl(),
                updatedPlan.getUser().getUserID(),
                workoutDTOS
        );
    }

    @Override
    public WorkoutPlanDTO getWorkoutPlan(Long planID) {
        WorkoutPlan plan = workoutPlanRepository.findById(planID)
                .orElseThrow(() -> new RuntimeException("Plan not found"));

        return new WorkoutPlanDTO(
                plan.getPlanID(),
                plan.getPlanName(),
                plan.getDescriptions(),
                plan.getDuration(),
                plan.getImageUrl(),
                plan.getUser() != null ? plan.getUser().getUserID() : null,
                plan.getWorkouts().stream()
                        .map(workout -> new WorkoutDTO(workout.getWorkoutID(),workout.getWorkoutName(), workout.getCategory(), workout.getInstructions(), workout.getImageUrl(), workout.getVideoUrl()))
                        .collect(Collectors.toList())
        );
    }

    @Override
    public List<WorkoutPlanDTO> getAllWorkoutPlans() {
        return workoutPlanRepository.findAll().stream()
                .map(plan -> new WorkoutPlanDTO(
                        plan.getPlanID(),
                        plan.getPlanName(),
                        plan.getDescriptions(),
                        plan.getDuration(),
                        plan.getImageUrl(),
                        plan.getUser() != null ? plan.getUser().getUserID() : null,
                        plan.getWorkouts().stream()
                                .map(workout -> new WorkoutDTO(workout.getWorkoutID(),workout.getWorkoutName(), workout.getCategory(), workout.getInstructions(), workout.getImageUrl(), workout.getVideoUrl()))
                                .collect(Collectors.toList())
                ))
                .collect(Collectors.toList());
    }
}
