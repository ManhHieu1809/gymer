package com.projectgym.service.Impl;

import com.projectgym.dto.ProgressDTO;
import com.projectgym.Entity.Progress;
import com.projectgym.Entity.User;
import com.projectgym.repository.ProgressRepository;
import com.projectgym.repository.MyAppUserRepository;
import com.projectgym.repository.WorkoutPlanRepository;
import com.projectgym.service.ProgressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProgressServiceImpl implements ProgressService {

    @Autowired
    private ProgressRepository progressRepository;

    @Autowired
    private MyAppUserRepository userRepository;

    @Autowired
    private WorkoutPlanRepository workoutPlanRepository;


    @Override
    public ProgressDTO addProgress(Long userID, ProgressDTO progressDTO) {
        // Tìm người dùng
        User user = userRepository.findById(userID)
                .orElseThrow(() -> new RuntimeException("User not found"));

        // Tạo mới tiến độ
        Progress progress = new Progress();
        progress.setProgressDate(progressDTO.getProgressDate());
        progress.setAchievement(progressDTO.getAchievement());
        progress.setUser(user);

        // Lưu tiến độ vào database
        progressRepository.save(progress);

        // Trả về ProgressDTO
        return new ProgressDTO(
                progress.getProgressDate(),
                progress.getAchievement(),
                user.getFullName() // Giả sử User entity có trường fullName

        );
    }


    @Override
    public List<ProgressDTO> getProgressByUser(Long userID) {
        // Tìm người dùng
        User user = userRepository.findById(userID)
                .orElseThrow(() -> new RuntimeException("User not found"));

        // Lấy danh sách tiến độ của người dùng
        List<Progress> progresses = progressRepository.findByUser(user);

        // Chuyển đổi sang ProgressDTO
        return progresses.stream()
                .map(progress -> new ProgressDTO(
                        progress.getProgressDate(),
                        progress.getAchievement(),
                        user.getFullName() // Hiển thị tên người dùng
                ))
                .collect(Collectors.toList());
    }

    @Override
    public ProgressDTO updateProgress(Long progressID, ProgressDTO progressDTO) {
        // Tìm tiến độ theo ID
        Progress progress = progressRepository.findById(progressID)
                .orElseThrow(() -> new RuntimeException("Progress not found with ID: " + progressID));

        // Cập nhật các thông tin mới
        progress.setProgressDate(progressDTO.getProgressDate());
        progress.setAchievement(progressDTO.getAchievement());

        // Lưu tiến độ
        progressRepository.save(progress);

        // Trả về DTO đã cập nhật
        return new ProgressDTO(
                progress.getProgressDate(),
                progress.getAchievement(),
                progress.getUser().getFullName()
        );
    }


    @Override
    public void deleteProgress(Long progressID) {
        // Xóa tiến độ
        progressRepository.deleteById(progressID);
    }

    @Override
    public List<ProgressDTO> searchProgressByUserName(String userName) {
        List<Progress> progresses = progressRepository.findByUserName(userName);

        // Chuyển đổi từ Progress sang ProgressDTO
        return progresses.stream()
                .map(progress -> new ProgressDTO(
                        progress.getProgressDate(),
                        progress.getAchievement(),
                        progress.getUser().getFullName()
                ))
                .collect(Collectors.toList());
    }

    @Override
    public List<ProgressDTO> getAllProgress() {
        return progressRepository.findAllProgress();
    }

    @Override
    public ProgressDTO updateProgressAchievement(Long progressID, Progress.Achievement newAchievement) {
        // Tìm tiến độ theo ID
        Progress progress = progressRepository.findById(progressID)
                .orElseThrow(() -> new RuntimeException("Progress not found with ID: " + progressID));

        // Cập nhật thành tựu mới
        progress.setAchievement(newAchievement);

        // Lưu tiến độ
        progressRepository.save(progress);

        // Trả về DTO đã cập nhật
        return new ProgressDTO(
                progress.getProgressID(),
                progress.getUser().getFullName(),
                progress.getWorkoutPlan().getPlanName(),
                progress.getProgressDate(),
                progress.getAchievement()
        );
    }

}
