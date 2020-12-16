package com.team3.weekday.action;

import com.team3.weekday.IDataBus;
import com.team3.weekday.constant.CommonConstant;
import com.team3.weekday.net.ChannelSession;
import com.team3.weekday.net.annotation.Action;
import com.team3.weekday.net.annotation.Command;
import com.team3.weekday.net.annotation.Param;
import com.team3.weekday.utils.ResponseUtil;
import com.team3.weekday.valueobject.Response;
import com.team3.weekday.valueobject.TaskStatus;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;

@Action
public class ProjectAction {

    @Autowired
    private IDataBus dataBus;

    @Command
    public Response createProject(@Param Long teamId,
                                  @Param String name,
                                  ChannelSession session) throws Exception {
        if (teamId == null) {
            return ResponseUtil.sendErrorMessage(CommonConstant.ERROR_TEAM_EMPTY);
        }

        return dataBus.projectService().createProject(session.getUserId(), teamId, name);
    }

    @Command
    public Response updateProject(@Param Long projectId,
                                  @Param String name,
                                  @Param String background,
                                  @Param String description,
                                  ChannelSession session) throws Exception {
        if (projectId == null) {
            return ResponseUtil.sendErrorMessage(CommonConstant.ERROR_PROJECT_EMPTY);
        }
        return dataBus.projectService().updateProject(session.getUserId(), projectId, name, background, description);
    }

    @Command
    public Response deleteProject(@Param Long projectId,
                                  ChannelSession session) throws Exception {
        if (projectId == null) {
            return ResponseUtil.sendErrorMessage(CommonConstant.ERROR_PROJECT_EMPTY);
        }
        return dataBus.projectService().deleteProject(session.getUserId(), projectId);
    }

    @Command
    public Response getProjectInfo(@Param Long projectId,
                                   ChannelSession session) throws Exception {
        if (projectId == null) {
            return ResponseUtil.sendErrorMessage(CommonConstant.ERROR_PROJECT_EMPTY);
        }
        return dataBus.projectService().getProjectInfo(projectId);
    }

    @Command
    public Response createTask(@Param Long projectId,
                               @Param String name,
                               @Param String content,
                               @Param Long ownerId,
                               @Param Long requesterId,
                               @Param Date start,
                               @Param Date end,
                               @Param Integer complexity,
                               ChannelSession session) throws Exception {
        if (projectId == null) {
            return ResponseUtil.sendErrorMessage(CommonConstant.ERROR_PROJECT_EMPTY);
        }
        return dataBus.projectService().createTask(projectId, name, content, ownerId, requesterId, start, end,
                null, complexity);
    }

    @Command
    public Response createSubTask(@Param String name,
                                  @Param Long ownerId,
                                  @Param Date start,
                                  @Param Date end,
                                  @Param Long masterId,
                                  ChannelSession session) throws Exception {
        if (masterId == null) {
            return ResponseUtil.sendErrorMessage(CommonConstant.ERROR_TASK_EMPTY);
        }

        return dataBus.projectService().createSubTask(name, ownerId, start, end, masterId);
    }

    @Command
    public Response updateTask(@Param Long taskId,
                               @Param String name,
                               @Param String content,
                               @Param Long ownerId,
                               @Param Long requesterId,
                               @Param Date start,
                               @Param Date end,
                               @Param Integer type,
                               @Param Long masterId,
                               @Param Integer complexity,
                               ChannelSession session) throws Exception {
        if (taskId == null) {
            return ResponseUtil.sendErrorMessage(CommonConstant.ERROR_TASK_EMPTY);
        }

        return dataBus.projectService().updateTask(taskId, name, content, ownerId, requesterId, start, end,
                type, masterId, complexity);
    }

    @Command
    public Response updateTaskStatus(@Param Long taskId,
                                     @Param Integer status,
                                     ChannelSession session) throws Exception {
        if (taskId == null) {
            return ResponseUtil.sendErrorMessage(CommonConstant.ERROR_TASK_EMPTY);
        }
        TaskStatus taskStatus = TaskStatus.getTaskStatus(status);
        if (taskStatus == null) {
            return ResponseUtil.sendErrorMessage(CommonConstant.PARAMETER_IS_WRONG, "taskStatus");
        }

        return dataBus.projectService().updateTaskStatus(taskId, taskStatus);
    }

    @Command
    public Response deleteTask(@Param Long taskId,
                               ChannelSession session) throws Exception {
        if (taskId == null) {
            return ResponseUtil.sendErrorMessage(CommonConstant.ERROR_TASK_EMPTY);
        }
        return dataBus.projectService().deleteTask(taskId);
    }

    @Command
    public Response getComments(@Param Long taskId,
                                ChannelSession session) throws Exception {
        if (taskId == null) {
            return ResponseUtil.sendErrorMessage(CommonConstant.ERROR_TASK_EMPTY);
        }
        return dataBus.projectService().getComments(session, taskId);
    }

    @Command
    public Response addComment(@Param Long taskId,
                               @Param String content,
                               ChannelSession session) throws Exception {
        if (taskId == null) {
            return ResponseUtil.sendErrorMessage(CommonConstant.ERROR_TASK_EMPTY);
        }
        return dataBus.projectService().addComment(session, taskId, content);
    }
}
