package com.projectgym.Entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "workout")
@Getter
@Setter
public class Workout {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "workoutID")
    private int WorkoutID;
    @Column(name = "workout_name")
    private String WorkoutName;
    @Column(name = "category")
    private String category;
    @Column(name = "instructions")
    private String instructions;

    @Column(name = "image_url")
    private String imageUrl;

    @Column(name = "video_url")
    private String videoUrl;


    @ManyToOne
    @JoinColumn(name = "planID",nullable = false)
    private WorkoutPlan workoutPlan;
}
