package com.projectgym.service.Impl;

import com.projectgym.dto.NutritionPlanDTO;
import com.projectgym.Entity.NutritionPlan;
import com.projectgym.Entity.User;
import com.projectgym.repository.NutritionPlanRepository;
import com.projectgym.repository.MyAppUserRepository;
import com.projectgym.service.NutritionPlanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class NutritionPlanServiceImpl implements NutritionPlanService {

    @Autowired
    private NutritionPlanRepository nutritionPlanRepository;

    @Autowired
    private MyAppUserRepository userRepository;

    @Override
    public NutritionPlanDTO createNutritionPlan(NutritionPlanDTO nutritionPlanDTO) {
        User user = userRepository.findById((long) nutritionPlanDTO.getUserID())
                .orElseThrow(() -> new RuntimeException("User not found with ID: " + nutritionPlanDTO.getUserID()));

        NutritionPlan nutritionPlan = new NutritionPlan();
        nutritionPlan.setNameNutritionPlan(nutritionPlanDTO.getNameNutritionPlan());
        nutritionPlan.setDescriptions(nutritionPlanDTO.getDescriptions());
        nutritionPlan.setCalo(nutritionPlanDTO.getCalo());
        nutritionPlan.setUser(user);

        nutritionPlan = nutritionPlanRepository.save(nutritionPlan);

        return new NutritionPlanDTO(nutritionPlan.getNutritionID(),
                nutritionPlan.getNameNutritionPlan(),
                nutritionPlan.getDescriptions(),
                nutritionPlan.getCalo(),
                nutritionPlan.getUser().getUserID());
    }

    @Override
    public NutritionPlanDTO updateNutritionPlan(Long nutritionID, NutritionPlanDTO nutritionPlanDTO) {
        NutritionPlan nutritionPlan = nutritionPlanRepository.findById(nutritionID)
                .orElseThrow(() -> new RuntimeException("Nutrition Plan not found"));

        nutritionPlan.setNameNutritionPlan(nutritionPlanDTO.getNameNutritionPlan());
        nutritionPlan.setDescriptions(nutritionPlanDTO.getDescriptions());
        nutritionPlan.setCalo(nutritionPlanDTO.getCalo());

        nutritionPlan = nutritionPlanRepository.save(nutritionPlan);

        return new NutritionPlanDTO(nutritionPlan.getNutritionID(),
                nutritionPlan.getNameNutritionPlan(),
                nutritionPlan.getDescriptions(),
                nutritionPlan.getCalo(),
                nutritionPlan.getUser().getUserID());
    }

    @Override
    public void deleteNutritionPlan(Long nutritionID) {
        NutritionPlan nutritionPlan = nutritionPlanRepository.findById(nutritionID)
                .orElseThrow(() -> new RuntimeException("Nutrition Plan not found"));
        nutritionPlanRepository.delete(nutritionPlan);
    }

    @Override
    public List<NutritionPlanDTO> getAllNutritionPlans() {
        return nutritionPlanRepository.findAll().stream()
                .map(plan -> new NutritionPlanDTO(
                        plan.getNutritionID(),
                        plan.getNameNutritionPlan(),
                        plan.getDescriptions(),
                        plan.getCalo(),
                        plan.getUser().getUserID()
                ))
                .collect(Collectors.toList());
    }

    @Override
    public List<NutritionPlanDTO> getNutritionPlansByUser(Long userID) {
        return nutritionPlanRepository.findByUser_UserID(userID).stream()
                .map(plan -> new NutritionPlanDTO(
                        plan.getNutritionID(),
                        plan.getNameNutritionPlan(),
                        plan.getDescriptions(),
                        plan.getCalo(),
                        plan.getUser().getUserID()
                ))
                .collect(Collectors.toList());
    }

    @Override
    public List<NutritionPlanDTO> suggestNutritionPlans(Long userID) {
        User user = userRepository.findById(userID)
                .orElseThrow(() -> new RuntimeException("User not found"));

        // Thiết lập mục tiêu mặc định
        String goal;
        if (user.getWeight() < 50) {
            goal = "gain muscle"; // Người có cân nặng thấp, mặc định là tăng cơ
        } else if (user.getWeight() > 80) {
            goal = "lose weight"; // Người có cân nặng cao, mặc định là giảm cân
        } else {
            goal = "maintain weight"; // Mặc định là duy trì cân nặng
        }

        // Tính toán nhu cầu calo
        double bmr;
        if ("male".equalsIgnoreCase(user.getGender())) {
            bmr = 88.36 + (13.4 * user.getWeight()) + (4.8 * user.getHeight()) - (5.7 * user.getAge());
        } else {
            bmr = 447.6 + (9.2 * user.getWeight()) + (3.1 * user.getHeight()) - (4.3 * user.getAge());
        }

        double targetCalo;
        switch (goal.toLowerCase()) {
            case "gain muscle":
                targetCalo = bmr * 1.2 + 500;
                break;
            case "lose weight":
                targetCalo = bmr * 1.2 - 500;
                break;
            default:
                targetCalo = bmr * 1.2;
        }

        // Gợi ý thực đơn dinh dưỡng
        return nutritionPlanRepository.findAll().stream()
                .filter(plan -> plan.getCalo() <= targetCalo + 300 && plan.getCalo() >= targetCalo - 200)
                .map(plan -> new NutritionPlanDTO(
                        plan.getNutritionID(),
                        plan.getNameNutritionPlan(),
                        plan.getDescriptions(),
                        plan.getCalo(),
                        plan.getUser().getUserID()
                ))
                .collect(Collectors.toList());
    }

    @Override
    public NutritionPlanDTO getNutritionPlanById(Long nutritionID) {
        // Tìm kế hoạch dinh dưỡng theo nutritionID
        NutritionPlan nutritionPlan = nutritionPlanRepository.findByNutritionID(nutritionID);

        // Kiểm tra nếu không tìm thấy
        if (nutritionPlan == null) {
            throw new RuntimeException("Kế hoạch dinh dưỡng không tồn tại");
        }

        // Chuyển từ NutritionPlan Entity sang NutritionPlanDTO
        return new NutritionPlanDTO(
                nutritionPlan.getNutritionID(),
                nutritionPlan.getNameNutritionPlan(),
                nutritionPlan.getDescriptions(),
                nutritionPlan.getCalo(),
                nutritionPlan.getUser().getUserID()
        );
    }
}
