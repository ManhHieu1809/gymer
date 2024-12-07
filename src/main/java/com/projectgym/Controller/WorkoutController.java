package com.projectgym.Controller;

import com.projectgym.dto.WorkoutDTO;
import com.projectgym.service.WorkoutService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/trainer/home/workouts")
public class WorkoutController {
    @Autowired
    private WorkoutService workoutService;

    // Lấy danh sách Workout theo PlanID
    @GetMapping("/plan/{planID}")
    public ResponseEntity<List<WorkoutDTO>> getWorkoutsByPlanID(@PathVariable Long planID) {
        List<WorkoutDTO> workouts = workoutService.getWorkoutByPlanID(planID);
        return ResponseEntity.ok(workouts);
    }

    // Lấy chi tiết Workout theo WorkoutID
    @GetMapping("/{workoutID}")
    public ResponseEntity<WorkoutDTO> getWorkoutByWorkoutID(@PathVariable Long workoutID) {
        WorkoutDTO workout = workoutService.getWorkoutByWorkoutID(workoutID);
        return ResponseEntity.ok(workout);
    }
}