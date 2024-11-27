package com.projectgym.Controller;

import com.projectgym.dto.ProgressDTO;
import com.projectgym.service.ProgressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("trainer/home/progress")
public class ProgressController {
    @Autowired
    private ProgressService progressService;

    @GetMapping("/search")
    public ResponseEntity<List<ProgressDTO>> searchProgressByUserName(
            @RequestParam String userName) {
        List<ProgressDTO> results = progressService.searchProgressByUserName(userName);
        return new ResponseEntity<>(results, HttpStatus.OK);
    }

    @PutMapping("/{progressID}/update")
    public ResponseEntity<ProgressDTO> updateProgress(
            @PathVariable Long progressID,
            @RequestBody ProgressDTO progressDTO) {
        ProgressDTO updatedProgress = progressService.updateProgress(progressID, progressDTO);
        return new ResponseEntity<>(updatedProgress, HttpStatus.OK);
    }

    @GetMapping("/{userID}")
    public ResponseEntity<List<ProgressDTO>> getUserProgress(@PathVariable Long userID) {
        List<ProgressDTO> progresses = progressService.getProgressByUser(userID);
        return new ResponseEntity<>(progresses, HttpStatus.OK);
    }

    @PostMapping("/{userID}/add")
    public ResponseEntity<ProgressDTO> addProgress(
            @PathVariable Long userID,
            @RequestBody ProgressDTO progressDTO) {
        ProgressDTO createdProgress = progressService.addProgress(userID, progressDTO);
        return new ResponseEntity<>(createdProgress, HttpStatus.CREATED);
    }

    // Xóa tiến độ của người dùng
    @DeleteMapping("/{progressID}")
    public ResponseEntity<Void> deleteProgress(@PathVariable Long progressID) {
        progressService.deleteProgress(progressID);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
