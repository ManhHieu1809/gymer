package com.projectgym.service.Impl;

import com.projectgym.Entity.FavoriteTopic;
import com.projectgym.Entity.Topic;
import com.projectgym.Entity.User;
import com.projectgym.dto.FavoriteTopicDTO;
import com.projectgym.dto.TopicDTO;
import com.projectgym.repository.MyAppUserRepository;
import com.projectgym.repository.TopicRepository;
import com.projectgym.service.TopicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TopicServiceImpl implements TopicService {

    @Autowired
    private TopicRepository topicRepository;
    @Autowired
    private MyAppUserRepository userRepository;

    @Override
    public TopicDTO createTopic(TopicDTO topicDTO) {
        Topic topic = new Topic();
        topic.setTopicName(topicDTO.getTopicName());
        topic.setDescriptions(topicDTO.getDescriptions());
        topic.setCreatedDate(Timestamp.valueOf(LocalDateTime.now())); // Tự động set thời gian tạo
        Topic savedTopic = topicRepository.save(topic);
        return convertToDTO(savedTopic);
    }

    @Override
    public TopicDTO updateTopic(Long topicID, TopicDTO topicDTO) {
        Topic topic = topicRepository.findById(topicID)
                .orElseThrow(() -> new RuntimeException("Topic not found with ID: " + topicID));
        topic.setTopicName(topicDTO.getTopicName());
        topic.setDescriptions(topicDTO.getDescriptions());
        Topic updatedTopic = topicRepository.save(topic);
        return convertToDTO(updatedTopic);
    }

    @Override
    public void deleteTopic(Long topicID) {
        if (!topicRepository.existsById(topicID)) {
            throw new RuntimeException("Topic not found with ID: " + topicID);
        }
        topicRepository.deleteById(topicID);
    }

    @Override
    public List<TopicDTO> getAllTopics() {
        List<Topic> topics = topicRepository.findAll();
        return topics.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    // Chuyển đổi từ Entity -> DTO
    private TopicDTO convertToDTO(Topic topic) {
        return new TopicDTO(
                topic.getTopicID(),
                topic.getTopicName(),
                topic.getDescriptions(),
                topic.getCreatedDate()
        );
    }

    @Override
    public List<FavoriteTopicDTO> getUserTopics(Long userID) {
        // Lấy thông tin user theo ID
        User user = userRepository.findById(userID)
                .orElseThrow(() -> new RuntimeException("User not found with ID: " + userID));

        // Lấy danh sách các topic liên kết với user và chuyển sang DTO
        return user.getFavoriteTopics().stream()
                .map(favoriteTopic -> new FavoriteTopicDTO(
                        favoriteTopic.getFavoriteID(),
                        favoriteTopic.getUser().getUserID(),
                        favoriteTopic.getTopic().getTopicID()
                ))
                .collect(Collectors.toList());
    }
}
