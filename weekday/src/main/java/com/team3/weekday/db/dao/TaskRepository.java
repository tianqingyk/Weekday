package com.team3.weekday.db.dao;

import com.team3.weekday.db.entity.Task;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * @author yangke
 * @projectName weekday
 * @date 2020-10-21
 */
public interface TaskRepository extends CrudRepository<Task, Long> {

    List<Task> findAllByMasterId(Long masterId);

    List<Task> findAllByProjectId(Long projectId);
}
