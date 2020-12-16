package com.team3.weekday.db.dao;

import com.team3.weekday.db.entity.Project;
import org.springframework.data.repository.CrudRepository;

/**
 * @author yangke
 * @projectName weekday
 * @date 2020-10-21
 */
public interface ProjectRepository extends CrudRepository<Project, Long> {
}
