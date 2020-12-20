package com.team3.weekday.db.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Date;

/**
 * @author yangke
 * @projectName weekday
 * @date 2020/12/8
 */
@Entity
@Data
public class TaskComment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long taskId;

    private String comment;

    private Long userId;

    private Date date;

    public TaskComment(){
    }
    public TaskComment(Long taskId, String comment, Long userId) {
        this.taskId = taskId;
        this.comment = comment;
        this.userId = userId;
        this.date = new Date();
    }

}
