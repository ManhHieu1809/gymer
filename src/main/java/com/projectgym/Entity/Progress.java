package com.projectgym.Entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@Entity
@Table(name = "progress")
public class Progress {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "progressID")
    private int progressID;
    @Column(name = "progress_date")
    @Temporal(TemporalType.DATE)
    private Date progressDate;
    @Enumerated(EnumType.STRING)
    private Achievement achievement;

    public enum Achievement {
        COMPLETED, IN_PROGRESS, NOT_STARTED
    }

    @ManyToOne
    @JoinColumn(name = "userID",nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "planID", nullable = false)
    private WorkoutPlan workoutPlan;
}
