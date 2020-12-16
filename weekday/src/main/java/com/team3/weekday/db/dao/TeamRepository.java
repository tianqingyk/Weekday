package com.team3.weekday.db.dao;

import com.team3.weekday.db.entity.Team;
import org.springframework.data.repository.CrudRepository;

/**
 * @author yangke
 * @title: TeamRepository
 * @projectName weekday
 * @date 2020-10-02
 */
public interface TeamRepository extends CrudRepository<Team, Long> {
}
