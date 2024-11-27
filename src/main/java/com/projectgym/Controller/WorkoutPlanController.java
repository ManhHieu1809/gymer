package com.projectgym.Controller;

import com.projectgym.dto.WorkoutPlanDTO;
import com.projectgym.service.WorkoutPlanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/trainer/home/workout-plans")
public class WorkoutPlanController {
    @Autowired
    private WorkoutPlanService workoutPlanService;

    @PostMapping
    public ResponseEntity<WorkoutPlanDTO> createWorkoutPlan(@RequestBody WorkoutPlanDTO workoutPlanDTO) {
        WorkoutPlanDTO createdPlan = workoutPlanService.createWorkoutPlan(workoutPlanDTO);
        return new ResponseEntity<>(createdPlan, HttpStatus.CREATED);
    }

    @PutMapping("/{planID}")
    public ResponseEntity<WorkoutPlanDTO> updateWorkoutPlan(@PathVariable Long planID, @RequestBody WorkoutPlanDTO workoutPlanDTO) {
        WorkoutPlanDTO updatedPlan = workoutPlanService.updateWorkoutPlan(planID, workoutPlanDTO);
        return new ResponseEntity<>(updatedPlan, HttpStatus.OK);
    }

    @DeleteMapping("/{planID}")
    public ResponseEntity<Void> deleteWorkoutPlan(@PathVariable Long planID) {
        workoutPlanService.deleteWorkoutPlan(planID);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/user/{userID}")
    public ResponseEntity<List<WorkoutPlanDTO>> getWorkoutPlansByUser(@PathVariable Long userID) {
        List<WorkoutPlanDTO> plans = workoutPlanService.getWorkoutPlansByUser(userID);
        return new ResponseEntity<>(plans, HttpStatus.OK);
    }

    @PutMapping("/{planID}/assign/{userID}")
    public ResponseEntity<WorkoutPlanDTO> assignPlanToUser(@PathVariable Long planID, @PathVariable Long userID) {
        WorkoutPlanDTO assignedPlan = workoutPlanService.assignPlanToUser(planID, userID);
        return new ResponseEntity<>(assignedPlan, HttpStatus.OK);
    }
}
