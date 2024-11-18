package com.projectgym.Entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;
import java.util.List;

@Entity
@Table(name = "topic")
@Getter
@Setter
public class Topic {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "topicID")
    private int topicID;
    @Column(name = "topic_name")
    private String topicName;
    @Column(name = "descriptions")
    private String descriptions;
    @Column(name = "created_date")
    private Timestamp createdDate;

    @OneToMany(mappedBy = "topic",cascade = CascadeType.ALL)
    private List<FavoriteTopic> favoriteTopics;
}
