package com.projectgym.Entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "Users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "userID")
    private int userID;
    @Column(name = "user_name")
    private String userName;
    @Column(name = "user_password")
    private String userPassword;
    @Column(name = "email")
    private String email;
    @Column(name = "full_name")
    private String fullName;
    @Column(name = "age")
    private int age;
    @Column(name = "gender")
    private String gender;
    @Column(name = "height")
    private double height;
    @Column(name = "weight")
    private double weight;
    @Enumerated(EnumType.STRING)
    @Column(name = "roles")
    private Role roles;

    public enum Role {
        ADMIN, CUSTOMER, TRAINER
    }


    public Role getRole() {
        return roles;
    }

    public void setRole(Role role) {
        this.roles = role;
    }

    // Quan hệ một-nhiều với WorkoutPlan
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<WorkoutPlan> workoutPlans;

    // Quan hệ một-nhiều với NutritionPlan
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<NutritionPlan> nutritionPlans;

    // Quan hệ một-nhiều với Notification
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Notification> notifications;

    // Quan hệ một-nhiều với Orders
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Orders> orders;

    // Quan hệ một-nhiều với FavoriteTopic
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<FavoriteTopic> favoriteTopics;

    // Quan hệ một-nhiều với PaymentMethod
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<PaymentMethod> paymentMethods;

    // Quan hệ một-nhiều với Progress
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Progress> progresses;
}
