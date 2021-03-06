package com.team3.weekday.db.entity;

import com.team3.weekday.valueobject.TaskStatus;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotEmpty;
import java.util.Date;

/**
 * @author yangke
 * @projectName weekday
 * @date 2020-10-21
 */

@Entity
@Data
public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private Long projectId;

    private String content;

    private Long ownerId;

    private Long requesterId;

    private Date start;

    private Date end;
    @NotEmpty(message = "type is empty!")
    private Integer type;

    private Long masterId;

    private Integer complexity;

    private Integer status = TaskStatus.BEFORE.getStatus();

    public Task() {
    }

    public Task(String name, Long projectId, String content, Long ownerId, Long requesterId, Date start, Date end,
                Integer type, Long masterId, Integer complexity) {
        this.name = name;
        this.projectId = projectId;
        this.content = content;
        this.ownerId = ownerId;
        this.requesterId = requesterId;
        this.start = start;
        this.end = end;
        this.type = type;
        this.masterId = masterId;
        this.complexity = complexity;
    }

}
