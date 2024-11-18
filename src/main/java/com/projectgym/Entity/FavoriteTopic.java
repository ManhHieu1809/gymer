package com.projectgym.Entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "favoritetopic")
public class FavoriteTopic {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "FavoriteID")
    private int FavoriteID;


    @ManyToOne
    @JoinColumn(name = "userID",nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "topicID",nullable = false)
    private Topic topic;
}
