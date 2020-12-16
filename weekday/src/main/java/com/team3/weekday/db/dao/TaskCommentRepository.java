package com.team3.weekday.db.dao;

import com.team3.weekday.db.entity.TaskComment;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * @author yangke
 * @projectName weekday
 * @date 2020/12/8
 */
public interface TaskCommentRepository extends CrudRepository<TaskComment, Long> {
    List<TaskComment> findAllByTaskId(Long taskId);
}
