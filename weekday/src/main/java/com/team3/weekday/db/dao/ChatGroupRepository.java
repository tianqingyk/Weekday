package com.team3.weekday.db.dao;

import com.team3.weekday.db.entity.ChatGroup;
import org.springframework.data.repository.CrudRepository;

/**
 * @author yangke
 * @projectName weekday
 * @date 2020-11-04
 */

public interface ChatGroupRepository extends CrudRepository<ChatGroup, Long> {
}
