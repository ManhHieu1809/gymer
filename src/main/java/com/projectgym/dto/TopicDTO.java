package com.projectgym.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Timestamp;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TopicDTO {
    private int topicID;
    private String topicName;
    private String descriptions;
    private Timestamp createdDate;

}
