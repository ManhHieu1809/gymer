package com.projectgym.dto;

import com.projectgym.Entity.User;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {
    private int userID;
    private String username;
    private String password;
    private String email;
    private String fullName;
    private int age;
    private String gender;
    private double height;
    private double weight;
    @Enumerated(EnumType.STRING)
    private Role roles;

    public UserDTO(User user) {
        this.userID = user.getUserID();
        this.username = user.getUserName();
        this.password = user.getUserPassword();
        this.email = user.getEmail();
        this.fullName = user.getFullName();
        this.age = user.getAge();
        this.gender = user.getGender();
        this.height = user.getHeight();
        this.weight = user.getWeight();
        this.roles = Role.valueOf(user.getRole().name());
    }

    public UserDTO(int userID, String userName, String fullName, String email) {
        this.userID = userID;
        this.username = userName;
        this.fullName = fullName;
        this.email = email;
    }

    public enum Role {
        ADMIN, CUSTOMER, TRAINER
    }
    public UserDTO(int userID, String userName, String userPassword, String email, String fullName, int age, String gender, double height, double weight, User.Role role) {
        this.userID = userID;
        this.username = userName;
        this.password = userPassword;
        this.email = email;
        this.fullName = fullName;
        this.age = age;
        this.gender = gender;
        this.height = height;
        this.weight = weight;
        this.roles = Role.valueOf(role.name());
    }



}
