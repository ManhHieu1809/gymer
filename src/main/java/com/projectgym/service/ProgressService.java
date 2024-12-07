package com.projectgym.service;

import com.projectgym.Entity.Progress;
import com.projectgym.dto.ProgressDTO;

import java.util.List;

public interface ProgressService {
    ProgressDTO addProgress(Long userID, ProgressDTO progressDTO);
    List<ProgressDTO> getProgressByUser(Long userID);
    ProgressDTO updateProgress(Long progressID, ProgressDTO progressDTO);
    void deleteProgress(Long progressID);
    List<ProgressDTO> searchProgressByUserName(String fullName);
    List<ProgressDTO> getAllProgress();
    ProgressDTO updateProgressAchievement(Long progressID, Progress.Achievement newAchievement);
}
