package com.team3.weekday.service.interf;

import com.team3.weekday.db.entity.User;
import com.team3.weekday.valueobject.Response;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import javax.transaction.Transactional;
import java.util.Date;
import java.util.Optional;

/**
 * @author yangke
 * @title: IUserService
 * @projectName weekday
 * @date 2020-09-15
 */
public interface IUserService {

    User creatUser(String username, String password, String email, Long phonenum, Date birthday, String label);

//    User createUser(OAuth2User principal);

    @Transactional
    User creatUserByGithub(Long githubId, String name, String avater);

    @Transactional
    User creatUserByGoogle(String googleId, String name, String avater);

    void removeUser(Long id);

    User updateUser(User user);

    Optional<User> getUserById(Long id);

    User getUserByName(String username);

    Page<User> listUsersByNameLike(String name, Pageable pageable);

    User getUserByEmail(String email);

    Response updateUserInfo(User user, String name, String avatar, String email, Long phonenum,
                            Date birthday, String label) throws Exception;

    //List<User> getUserListByName(String name);
}
