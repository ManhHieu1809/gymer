package com.projectgym.Controller;

import com.projectgym.dto.FavoriteTopicDTO;
import com.projectgym.dto.TopicDTO;
import com.projectgym.service.TopicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/trainer/home/topics")
public class TopicController {

    @Autowired
    private TopicService topicService;

    // Tạo mới một chủ đề luyện tập
    @PostMapping
    public ResponseEntity<TopicDTO> createTopic(@RequestBody TopicDTO topicDTO) {
        TopicDTO createdTopic = topicService.createTopic(topicDTO);
        return new ResponseEntity<>(createdTopic, HttpStatus.CREATED); // 201 Created
    }

    // Chỉnh sửa thông tin chủ đề luyện tập
    @PutMapping("/{topicID}")
    public ResponseEntity<TopicDTO> updateTopic(
            @PathVariable Long topicID,
            @RequestBody TopicDTO topicDTO
    ) {
        TopicDTO updatedTopic = topicService.updateTopic(topicID, topicDTO);
        return ResponseEntity.ok(updatedTopic); // 200 OK
    }

    // Xóa chủ đề luyện tập
    @DeleteMapping("/{topicID}")
    public ResponseEntity<Void> deleteTopic(@PathVariable Long topicID) {
        topicService.deleteTopic(topicID);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT); // 204 No Content
    }

    // Lấy danh sách tất cả các chủ đề
    @GetMapping
    public ResponseEntity<List<TopicDTO>> getAllTopics() {
        List<TopicDTO> topics = topicService.getAllTopics();
        return ResponseEntity.ok(topics); // 200 OK
    }

    // Lấy danh sách chủ đề theo userID
    @GetMapping("/user/{userID}")
    public ResponseEntity<List<FavoriteTopicDTO>> getTopicsByUserID(@PathVariable Long userID) {
        List<FavoriteTopicDTO> topics = topicService.getUserTopics(userID);
        return ResponseEntity.ok(topics); // 200 OK
    }

}
