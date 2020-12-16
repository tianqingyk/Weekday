package com.team3.weekday.service.interf;

import com.alibaba.fastjson.JSONObject;
import com.team3.weekday.db.entity.Project;
import com.team3.weekday.db.entity.Task;
import com.team3.weekday.net.ChannelSession;
import com.team3.weekday.utils.ErrorMessageException;
import com.team3.weekday.valueobject.Response;
import com.team3.weekday.valueobject.TaskStatus;

import java.util.Date;

public interface IProjectService {

    JSONObject projectToJson(Project project);

    Response createProject(Long userId, Long teamId, String name) throws Exception;

    Response updateProject(Long userId, Long projectId, String name, String background, String description) throws Exception;

    Response createTask(Long projectId, String name, String content, Long ownerId, Long requesterId, Date start, Date end,
                         Long masterId, Integer complexity)
            throws Exception;

    Response updateTask(Long id, String name, String content, Long ownerId, Long requesterId, Date start, Date end,
                        Integer type, Long masterId, Integer complexity)
            throws Exception;

    Response updateTaskStatus(Long id, TaskStatus taskStatus) throws Exception;

    Response deleteProject(Long userId, Long projectId) throws Exception;

    Response getProjectInfo(Long projectId) throws Exception;

    Response createSubTask(String name, Long ownerId, Date start, Date end, Long masterId)  throws Exception;

    void deleteSubTask(Task subTask) throws Exception;

    void deleteTask(Task task) throws Exception;

    Response deleteTask(Long taskId) throws Exception;

    Response getComments(ChannelSession session, Long taskId) throws ErrorMessageException;

    Response addComment(ChannelSession session, Long taskId, String content) throws ErrorMessageException;
}
