package com.team3.weekday.db.entity;

import com.alibaba.fastjson.annotation.JSONField;
import com.team3.weekday.constant.CommonConstant;
import com.team3.weekday.utils.StringBuilderUtil;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import java.util.List;

/**
 * @author yangke
 * @title: User
 * @projectName weekday
 * @date 2020-09-15
 */

@Entity
public class User implements UserDetails, Serializable {

    @Id // Key
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String oAuth2Id;

    @Column(nullable = false, length = 20)
    private String name;

    @NotEmpty(message = "Username is empty!")
    @Size(min = 3, max = 40)
    @Column(nullable = false, length = 40, unique = true)
    private String username;

    @NotEmpty(message = "Passward is empty!")
    @Size(max = 50)
    @Column(length = 100)
    @JSONField(serialize = false)
    private String password;

    @Column(length = 200)
    private String avatar = CommonConstant.DEFAULT_AVATAR;

    @NotEmpty(message = "Email is empty!")
    @Column(unique = true)
    private String email;

    private Long phonenum;

    private Date birthday;

    private String label;

    private String teams = "";

    private String activeCode;

    private Integer activeStatus; // 0:non-active 1:active

    @Column(length = 20, unique = true)
    private Long githubId;

    @Column(length = 50, unique = true)
    private String googleId;

    public User(Long githubId, String name, String avater) {
        this.githubId = githubId;
        this.name = name;
        this.avatar = avater;
        this.username =  "github"+githubId;
    }

    public User(String googleId, String name, String avater) {
        this.googleId = googleId;
        this.name = name;
        this.avatar = avater;
        this.username =  "google"+googleId;
    }

    public User(String username, String password, String email, Long phonenum, Date birthday, String label) {
        this.name = username;
        this.username = username;
        this.password = password;
        this.email = email;
        this.phonenum = phonenum;
        this.birthday = birthday;
        this.label = label;
    }

    public User(){

    }

    //    public User(String oAuth2Id) {
//        this.oAuth2Id = oAuth2Id;
//        this.username = oAuth2Id;
//        this.password = oAuth2Id;
//    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Long getPhonenum() {
        return phonenum;
    }

    public void setPhonenum(Long phonenum) {
        this.phonenum = phonenum;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
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

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getTeams() {
        return teams;
    }

    public void setTeams(String teams) {
        this.teams = teams;
    }

    public List<Long> getTeamList() {
        return StringBuilderUtil.stringToList(this.teams);
    }


    public String getActiveCode() {
        return activeCode;
    }

    public void setActiveCode(String activeCode) {
        this.activeCode = activeCode;
    }

    public Integer getActiveStatus() {
        return activeStatus;
    }

    public void setActiveStatus(Integer activeStatus) {
        this.activeStatus = activeStatus;
    }

    public Long getGithubId() {
        return githubId;
    }

    public void setGithubId(Long githubId) {
        this.githubId = githubId;
    }

    public String getGoogleId() {
        return googleId;
    }

    public void setGoogleId(String googleId) {
        this.googleId = googleId;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    @JSONField(serialize = false)
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    @JSONField(serialize = false)
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    @JSONField(serialize = false)
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    @JSONField(serialize = false)
    public boolean isEnabled() {
        return true;
    }

    public void addTeam(Long teamId) {
        this.setTeams(StringBuilderUtil.addElement(teamId,this.teams));
    }

    public void deleteTeam(Long teamId){
        this.setTeams(StringBuilderUtil.deleteElement(teamId,this.teams));
    }
}
