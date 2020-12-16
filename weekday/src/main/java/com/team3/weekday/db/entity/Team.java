package com.team3.weekday.db.entity;

import com.team3.weekday.utils.StringBuilderUtil;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.List;

/**
 * @author yangke
 * @title: Team
 * @projectName weekday
 * @date 2020-10-02
 */

@Entity
public class Team {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private Long ownerId;

    // userId1;userId2
    private String members = "";

    // projectId;projectId
    private String projects = "";

    public Team() {
    }

    public Team(String name, Long ownerId) {
        this.name = name;
        this.ownerId = ownerId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(Long ownerId) {
        this.ownerId = ownerId;
    }

    public String getProjects() {
        return projects;
    }

    public void setProjects(String projects) {
        this.projects = projects;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMembers() {
        return members;
    }

    public void setMembers(String members) {
        this.members = members;
    }

    public List<Long> getMemberList() {
        return StringBuilderUtil.stringToList(this.members);
    }

    public List<Long> getProjectList() {
        return StringBuilderUtil.stringToList(this.projects);
    }

    public void addProject(Project project) {
        this.setProjects(StringBuilderUtil.addElement(project.getId(),this.projects));
    }

    public void addMember(Long memberId) {
        this.setMembers(StringBuilderUtil.addElement(memberId,this.members));
    }

    public void deleteMember(Long memberId) {
        this.setMembers(StringBuilderUtil.deleteElement(memberId, this.members));
    }

    public void deleteProject(Long projectId){
        this.setProjects(StringBuilderUtil.deleteElement(projectId,this.projects));
    }

}
