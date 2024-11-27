package com.projectgym.service;

import com.projectgym.dto.FavoriteTopicDTO;
import com.projectgym.dto.TopicDTO;

import java.util.List;

public interface TopicService {
    TopicDTO createTopic(TopicDTO topicDTO);
    TopicDTO updateTopic(Long topicID, TopicDTO topicDTO);
    void deleteTopic(Long topicID);
    List<TopicDTO> getAllTopics();
    List<FavoriteTopicDTO> getUserTopics(Long userID);
}
