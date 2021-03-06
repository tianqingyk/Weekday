package com.team3.weekday.db.entity;

import com.team3.weekday.constant.CommonConstant;
import com.team3.weekday.utils.StringBuilderUtil;
import lombok.Data;

import javax.persistence.*;
import java.util.List;

/**
 * @author yangke
 * @projectName weekday
 * @date 2020-10-21
 */
@Entity
@Data
public class Project {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long teamId;

    private String name;

    private String tasks = "";

    @Column(length = 200)
    private String background;

    private String description;

    public Project() {
    }

    public Project(Long teamId, String name) {
        this.teamId = teamId;
        this.name = name;
        this.background = CommonConstant.DEFAULT_AVATAR;//PLEASE CHANGE TO DEFAULT_BACKGROUND
    }

    public Project(Long teamId, String name, String background, String description) {
        this.teamId = teamId;
        this.name = name;
        this.background = background;
        this.description = description;
    }


    public List<Long> getTaskList() {
        return StringBuilderUtil.stringToList(this.tasks);
    }

    public void addTask(Task task) {
        this.setTasks(StringBuilderUtil.addElement(task.getId(),this.tasks));
    }

    public void deleteTask(Long taskId){
        this.setTasks(StringBuilderUtil.deleteElement(taskId,this.tasks));
    }

}
