package com.projectgym.Controller;

import com.projectgym.dto.NutritionPlanDTO;
import com.projectgym.service.NutritionPlanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/trainer/home/nutrition-plans")
public class NutritionPlanController {

    @Autowired
    private NutritionPlanService nutritionPlanService;

    // Tạo thực đơn
    @PostMapping
    public ResponseEntity<NutritionPlanDTO> createNutritionPlan(@RequestBody NutritionPlanDTO nutritionPlanDTO) {
        NutritionPlanDTO createdPlan = nutritionPlanService.createNutritionPlan(nutritionPlanDTO);
        return ResponseEntity.status(201).body(createdPlan);
    }

    // Cập nhật thực đơn
    @PutMapping("/{nutritionID}")
    public ResponseEntity<NutritionPlanDTO> updateNutritionPlan(
            @PathVariable Long nutritionID,
            @RequestBody NutritionPlanDTO nutritionPlanDTO) {
        NutritionPlanDTO updatedPlan = nutritionPlanService.updateNutritionPlan(nutritionID, nutritionPlanDTO);
        return ResponseEntity.ok(updatedPlan);
    }

    // Xóa thực đơn
    @DeleteMapping("/{nutritionID}")
    public ResponseEntity<Void> deleteNutritionPlan(@PathVariable Long nutritionID) {
        nutritionPlanService.deleteNutritionPlan(nutritionID);
        return ResponseEntity.noContent().build();
    }

    // Lấy tất cả thực đơn
    @GetMapping
    public ResponseEntity<List<NutritionPlanDTO>> getAllNutritionPlans() {
        List<NutritionPlanDTO> plans = nutritionPlanService.getAllNutritionPlans();
        return ResponseEntity.ok(plans);
    }

    // Lấy thực đơn theo userID
    @GetMapping("/user/{userID}")
    public ResponseEntity<List<NutritionPlanDTO>> getNutritionPlansByUser(@PathVariable Long userID) {
        List<NutritionPlanDTO> userPlans = nutritionPlanService.getNutritionPlansByUser(userID);
        return ResponseEntity.ok(userPlans);
    }

    // Gợi ý thực đơn cho userID
    @GetMapping("/suggestions/{userID}")
    public ResponseEntity<List<NutritionPlanDTO>> suggestNutritionPlans(@PathVariable Long userID) {
        List<NutritionPlanDTO> suggestions = nutritionPlanService.suggestNutritionPlans(userID);
        return ResponseEntity.ok(suggestions);
    }

    // Lấy thực đơn theo nutritionID
    @GetMapping("/{nutritionID}")
    public NutritionPlanDTO getNutritionPlan(@PathVariable Long nutritionID) {
        return nutritionPlanService.getNutritionPlanById(nutritionID);
    }
}
