package com.projectgym.dto;

import com.projectgym.Entity.Progress;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProgressDTO {
    private int progressID;
    @Temporal(TemporalType.DATE)
    private Date progressDate;
    private Progress.Achievement achievement;
    private String userName;
    private String planName;

    // Constructor
    public ProgressDTO(int progressID, Date progressDate, Progress.Achievement achievement, String userFullName) {
        this.progressID = progressID;
        this.progressDate = progressDate;
        this.achievement = achievement;
        this.userName = userFullName;
    }
    // Constructor
    public ProgressDTO(int progressID, String userFullName, String planName, Date progressDate, Progress.Achievement achievement) {
        this.progressID = progressID;
        this.userName = userFullName;
        this.planName = planName;
        this.progressDate = progressDate;
        this.achievement = achievement;
    }

}
