package com.projectgym.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class FavoriteTopicDTO {
    private int FavoriteID;
    private int userID;
    private int topicID;

}
