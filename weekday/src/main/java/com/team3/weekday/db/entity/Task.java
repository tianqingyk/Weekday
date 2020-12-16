package com.team3.weekday.db.entity;

import com.team3.weekday.valueobject.TaskStatus;

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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getProjectId() {
        return projectId;
    }

    public void setProjectId(Long projectId) {
        this.projectId = projectId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Long getOwnerId() { return ownerId; }

    public void setOwnerId(Long ownerId) { this.ownerId = ownerId; }

    public Long getRequesterId() { return requesterId; }

    public void setRequesterId(Long requesterId) { this.requesterId = requesterId; }

    public Date getStart() { return start; }

    public void setStart(Date start) { this.start = start; }

    public Date getEnd() { return end; }

    public void setEnd(Date end) { this.end = end; }

    public Integer getType() { return type; }

    public void setType(Integer type) { this.type = type; }

    public Long getMasterId() { return masterId; }

    public void setMasterId(long masterId) { this.masterId = masterId; }

    public Integer getComplexity() { return complexity; }

    public void setComplexity(Integer complexity) {
        this.complexity = complexity; }

    public int getStatus() { return status; }

    public void setStatus(int status) { this.status = status; }
}
