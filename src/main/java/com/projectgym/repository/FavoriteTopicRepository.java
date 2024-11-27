package com.projectgym.repository;

import com.projectgym.Entity.FavoriteTopic;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FavoriteTopicRepository extends JpaRepository<FavoriteTopic, Long> {
}
