package com.projectgym.repository;

import com.projectgym.Entity.Workout;
import com.projectgym.dto.WorkoutDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface WorkoutRepository extends JpaRepository<Workout, Long> {
    // Lấy danh sách Workout theo PlanID
    @Query("SELECT new com.projectgym.dto.WorkoutDTO(w.WorkoutID,w.WorkoutName, w.category, w.instructions,w.imageUrl,w.videoUrl) " +
            "FROM Workout w WHERE w.workoutPlan.planID = :planID")
    List<WorkoutDTO> findWorkoutsByPlanID(@Param("planID") Long planID);

    // Lấy chi tiết Workout theo WorkoutID
    @Query("SELECT new com.projectgym.dto.WorkoutDTO(w.WorkoutID,w.WorkoutName, w.category, w.instructions,w.imageUrl,w.videoUrl) " +
            "FROM Workout w WHERE w.WorkoutID = :workoutID")
    Optional<WorkoutDTO> findWorkoutByWorkoutID(@Param("workoutID") Long workoutID);
}
