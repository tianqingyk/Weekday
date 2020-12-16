package com.team3.weekday.db.dao;

import com.team3.weekday.db.entity.FriendList;

import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;


public interface FriendListRepository extends CrudRepository<FriendList, Long> {

    FriendList findByUserId1AndUserId2(Long userId1, Long userId2);

    List<FriendList> findByUserId1(Long userId1);
}