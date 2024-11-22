package com.projectgym.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class WorkoutPlanDTO {
    private int planID;
    private String planName;
    private String descriptions;
    private String duration;
}
