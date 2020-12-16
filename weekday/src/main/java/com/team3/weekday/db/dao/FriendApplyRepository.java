package com.team3.weekday.db.dao;

import com.team3.weekday.db.entity.FriendApply;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface FriendApplyRepository extends CrudRepository<FriendApply, Long> {
    FriendApply findByUserId1AndUserId2(Long userId1, Long userId2);

    List<FriendApply> findByUserId1(Long userId);

    List<FriendApply> findByUserId2(Long userId);
}
