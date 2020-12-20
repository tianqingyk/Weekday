package com.team3.weekday.db.entity;

import com.alibaba.fastjson.annotation.JSONField;
import com.team3.weekday.constant.CommonConstant;
import com.team3.weekday.utils.StringBuilderUtil;
import lombok.Data;
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
@Data
public class User implements UserDetails, Serializable {

    @Id
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

    /**
     * 0:non-active 1:active
     */
    private Integer activeStatus;

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


    public List<Long> getTeamList() {
        return StringBuilderUtil.stringToList(this.teams);
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
