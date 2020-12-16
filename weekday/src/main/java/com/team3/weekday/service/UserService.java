package com.team3.weekday.service;


import com.team3.weekday.DataBus;
import com.team3.weekday.constant.CommonConstant;
import com.team3.weekday.db.entity.User;
import com.team3.weekday.service.interf.IUserService;
import com.team3.weekday.utils.ResponseUtil;
import com.team3.weekday.valueobject.Response;
import com.team3.weekday.valueobject.ResponseState;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Date;
import java.util.Optional;
import java.util.UUID;

/**
 * @author yangke
 * @title: Userservice
 * @projectName weekday
 * @date 2020-09-15
 */

@Service
public class UserService implements IUserService, UserDetailsService {

    @Autowired
    private DataBus dataBus;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return dataBus.userRepository().findByUsername(username);
    }

    @Transactional
    @Override
    public User creatUser(String username, String password, String email, Long phonenum, Date birthday, String label) {
        User user = new User(username, password, email, phonenum, birthday, label);
        String activeCode = UUID.randomUUID().toString();
        user.setActiveCode(activeCode);
        user.setActiveStatus(0); //non-active
        return dataBus.userRepository().save(user);
    }

    @Transactional
    @Override
    public User creatUserByGithub(Long githubId, String name, String avater) {
        User user = new User(githubId, name, avater);
        String activeCode = UUID.randomUUID().toString();
        user.setActiveCode(activeCode);
        user.setActiveStatus(0); //non-active
        return dataBus.userRepository().save(user);
    }

    @Override
    public User creatUserByGoogle(String googleId, String name, String avater) {
        User user = new User(googleId, name, avater);
        String activeCode = UUID.randomUUID().toString();
        user.setActiveCode(activeCode);
        user.setActiveStatus(0); //non-active
        return dataBus.userRepository().save(user);
    }

    @Transactional
    @Override
    public void removeUser(Long id) {
        dataBus.userRepository().deleteById(id);
    }

    @Transactional
    @Override
    public User updateUser(User user) {
        return dataBus.userRepository().save(user);
    }

    @Override
    public Optional<User> getUserById(Long id) {
        return dataBus.userRepository().findById(id);
    }

    @Override
    public User getUserByEmail(String email) {
        return dataBus.userRepository().findByEmail(email);
    }

    @Override
    public User getUserByName(String username) {
        return dataBus.userRepository().findByUsername(username);
    }

    @Override
    public Page<User> listUsersByNameLike(String name, Pageable pageable) {
        name = "%" + name + "%";
        return dataBus.userRepository().findByNameLike(name, pageable);
    }

    @Override
    public Response updateUserInfo(User user, String name, String avatar, String email, Long phonenum, Date birthday, String label)
            throws Exception {
        if (user == null) {
            ResponseUtil.sendErrorMessage(CommonConstant.PARAMETER_IS_WRONG);
        }
        if (!name.isBlank()){
            user.setName(name);
        }
        if (!avatar.isBlank()){
            user.setAvatar(avatar);
        }
        if (!email.isBlank()){
            user.setEmail(email);
        }
        if (phonenum != null) {
            user.setPhonenum(phonenum);
        }
        if (birthday != null) {
            user.setBirthday(birthday);
        }
        if (!label.isBlank()){
            user.setLabel(label);
        }
        updateUser(user);
        return ResponseUtil.sendBody(ResponseState.OK, user);
    }
    /* no use
    @Override
    public List<User> getUserListByName(String name) {
        return dataBus.userRepository().findByNameLike("%"+name+"%");
    }
     */
}
