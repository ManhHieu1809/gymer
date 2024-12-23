package com.projectgym.dto;

import com.projectgym.Entity.Workout;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class WorkoutDTO {
    private int WorkoutID;
    private String WorkoutName;
    private String category;
    private String instructions;
    private String imageUrl;
    private String videoUrl;

    public WorkoutDTO(Workout workout){
    }
}
