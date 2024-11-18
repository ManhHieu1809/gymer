package com.projectgym.Entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "workoutplan")
public class WorkoutPlan {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "planID")
    private int planID;
    @Column(name = "planName")
    private String planName;
    @Column(name = "descriptions")
    private String descriptions;
    @Column(name = "duration")
    private String duration;

    @ManyToOne
    @JoinColumn(name = "userID",nullable = false)
    private User user;

    @OneToMany(mappedBy = "workoutPlan",cascade = CascadeType.ALL)
    private List<Workout> workouts;
}
