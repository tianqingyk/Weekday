package com.team3.weekday.db.entity;

import com.team3.weekday.utils.StringBuilderUtil;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.List;

/**
 * @author yangke
 * @projectName weekday
 * @date 2020-11-03
 */
// TODO Ke Yang 不用写 set和get
@Entity
public class ChatGroup {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private Long ownerId;

    // userId1;userId2
    private String members = "";

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

    public Long getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(Long ownerId) {
        this.ownerId = ownerId;
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

}
