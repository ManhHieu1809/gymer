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
    @Temporal(TemporalType.DATE)
    private Date progressDate;
    private Progress.Achievement achievement;
    private String userName;
}
