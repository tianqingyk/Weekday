package com.team3.weekday.db.dao;

import com.team3.weekday.db.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author yangke
 * @title: IUserRepository
 * @projectName weekday
 * @date 2020-09-15
 */
public interface UserRepository extends JpaRepository<User, Long> {

    Page<User> findByNameLike(String name, Pageable pageable);

    User findByUsername(String username);

    User findByActiveCode(String code);

    User findByEmail(String email);

    User findByGithubId(Long githubId);

    User findByGoogleId(String googleId);

    //List<User> findByNameLike(String name);
}
