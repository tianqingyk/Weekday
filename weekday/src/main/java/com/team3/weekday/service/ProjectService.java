package com.team3.weekday.service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.team3.weekday.IDataBus;
import com.team3.weekday.constant.CommonConstant;
import com.team3.weekday.constant.NotifyConstant;
import com.team3.weekday.db.entity.Project;
import com.team3.weekday.db.entity.Task;
import com.team3.weekday.db.entity.TaskComment;
import com.team3.weekday.db.entity.Team;
import com.team3.weekday.net.ChannelSession;
import com.team3.weekday.service.interf.IProjectService;
import com.team3.weekday.utils.ErrorMessageException;
import com.team3.weekday.utils.ResponseUtil;
import com.team3.weekday.valueobject.Response;
import com.team3.weekday.valueobject.ResponseState;
import com.team3.weekday.valueobject.TaskStatus;
import com.team3.weekday.valueobject.TaskType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class ProjectService implements IProjectService {
    @Autowired
    private IDataBus dataBus;

    @Override
    public JSONObject projectToJson(Project project) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("projectId", project.getId());
        jsonObject.put("name", project.getName());
        jsonObject.put("teamId", project.getTeamId());
        jsonObject.put("background", project.getBackground());
        jsonObject.put("description", project.getDescription());

        JSONArray tasks = new JSONArray();

        if (project.getTaskList() != null) {
            for (Long taskId : project.getTaskList()) {
                Optional<Task> taskOptional = dataBus.taskRepository().findById(taskId);
                if (taskOptional.isPresent()) {
                    tasks.add(taskOptional.get());
                }
            }
        }
        jsonObject.put("tasks", tasks);

        return jsonObject;
    }

    @Override
    public Response createProject(Long userId, Long teamId, String name) throws Exception {
        //team members can create project
        Optional<Team> teamOptional = dataBus.teamRepository().findById(teamId);
        if (teamOptional.isEmpty()) {
            return ResponseUtil.sendErrorMessage(CommonConstant.TEAM_NOT_EXIST);
        }

        Team team = teamOptional.get();
        if (!team.getMembers().contains(userId.toString())) {
            return ResponseUtil.sendErrorMessage(CommonConstant.PERMISSION_DENIED, "member not belong this team");
        }
        Project project = new Project(teamId, name);
        project = dataBus.projectRepository().save(project);
        team.addProject(project);
        dataBus.teamRepository().save(team);

        dataBus.teamService().notifyTeam(teamId, NotifyConstant.NOTIFT_CREATE_PROJECT,dataBus.projectService().projectToJson(project));
        return ResponseUtil.send(ResponseState.OK);
    }

    @Override
    public Response updateProject(Long userId, Long projectId, String name, String background, String description) throws Exception {
        //team members can delete project
        Optional<Project> projectOptional = dataBus.projectRepository().findById(projectId);
        if (projectOptional.isEmpty()) {
            return ResponseUtil.sendErrorMessage(CommonConstant.PROJECT_NOT_EXIST);
        }
        Project project = projectOptional.get();

        Optional<Team> teamOptional = dataBus.teamRepository().findById(project.getTeamId());
        if (teamOptional.isEmpty()) {
            return ResponseUtil.sendErrorMessage(CommonConstant.TEAM_NOT_EXIST);
        }
        Team team = teamOptional.get();

        if (!team.getMembers().contains(userId.toString())) {
            return ResponseUtil.sendErrorMessage(CommonConstant.PERMISSION_DENIED, "member not belong this team");
        }
        if (!name.isBlank()) {
            project.setName(name);
        }
        if (!background.isBlank()) {
            project.setBackground(background);
        }
        if (!description.isBlank()) {
            project.setDescription(description);
        }
        dataBus.projectRepository().save(project);

        ResponseUtil.notifyUsersBody(team.getMemberList(), NotifyConstant.NOTIFY_UPDATE_PROJECT, projectToJson(project));
        return ResponseUtil.send(ResponseState.OK);
    }

    @Override
    public Response deleteProject(Long userId, Long projectId) throws Exception {
        //only the team owner can delete project
        Optional<Project> projectOptional = dataBus.projectRepository().findById(projectId);
        if (projectOptional.isEmpty()) {
            return ResponseUtil.sendErrorMessage(CommonConstant.PROJECT_NOT_EXIST);
        }
        Project project = projectOptional.get();

        Optional<Team> teamOptional = dataBus.teamRepository().findById(project.getTeamId());
        if (teamOptional.isEmpty()) {
            return ResponseUtil.sendErrorMessage(CommonConstant.TEAM_NOT_EXIST);
        }
        Team team = teamOptional.get();

        if (!team.getOwnerId().equals(userId)) {
            return ResponseUtil.sendErrorMessage(CommonConstant.PERMISSION_DENIED, "member not belong this team");
        }

        team.deleteProject(projectId);
        dataBus.teamRepository().save(team);
        dataBus.projectRepository().delete(project);
        //delete task
        List<Task> TaskList = dataBus.taskRepository().findAllByProjectId(projectId);
        for (Task task : TaskList) {
            dataBus.taskRepository().delete(task);
        }

        dataBus.teamService().notifyTeam(team.getId(), NotifyConstant.NOTIFY_DELETE_PROJECT, projectId);
        return ResponseUtil.send(ResponseState.OK);
    }

    @Override
    public Response getProjectInfo(Long projectId) throws Exception {
        //anyone can check project info
        Optional<Project> projectOptional = dataBus.projectRepository().findById(projectId);
        if (projectOptional.isEmpty()) {
            return ResponseUtil.sendErrorMessage(CommonConstant.PROJECT_NOT_EXIST);
        }
        Project project = projectOptional.get();
        return ResponseUtil.sendBody(ResponseState.OK, dataBus.projectService().projectToJson(project));
    }

    @Override
    public Response createTask(Long projectId, String name, String content, Long ownerId, Long requesterId,
                               Date start, Date end, Long masterId, Integer complexity)
            throws Exception {
//        if (TaskType.getTaskType(type) != TaskType.Task) {
//            return ResponseUtil.sendErrorMessage(CommonConstant.PARAMETER_IS_WRONG,"taskType");
//        }
        Optional<Project> projectOptional = dataBus.projectRepository().findById(projectId);
        if (projectOptional.isEmpty()) {
            return ResponseUtil.sendErrorMessage(CommonConstant.PROJECT_NOT_EXIST);
        }
        Project project = projectOptional.get();
        Integer type = TaskType.Task.getType();
        Task task = new Task(name, projectId, content, ownerId, requesterId, start, end,
                type, masterId, complexity);
        dataBus.taskRepository().save(task);
        project.addTask(task);
        dataBus.projectRepository().save(project);

        dataBus.teamService().notifyTeam(project.getTeamId(), NotifyConstant.NOTIFY_CREATE_TASK, task);
        return ResponseUtil.send(ResponseState.OK);
    }

    @Override
    public Response createSubTask(String name, Long ownerId, Date start, Date end, Long masterId)
            throws Exception {

        Optional<Task> taskOptional = dataBus.taskRepository().findById(masterId);
        if (taskOptional.isEmpty()) {
            return ResponseUtil.sendErrorMessage(CommonConstant.TASK_NOT_EXIST);
        }
        Task masterTask = taskOptional.get();
        if (TaskType.getTaskType(masterTask.getType()) != TaskType.Task) {
            return ResponseUtil.sendErrorMessage(CommonConstant.ERROR_MESSAGE, "master task type wrong");
        }
        Long projectId = masterTask.getProjectId();
        Optional<Project> projectOptional = dataBus.projectRepository().findById(projectId);
        if (projectOptional.isEmpty()) {
            return ResponseUtil.sendErrorMessage(CommonConstant.PROJECT_NOT_EXIST);
        }
        Project project = projectOptional.get();

        Integer type = TaskType.SubTask.getType();
        Task subTask = new Task(name, projectId, null, ownerId, null, start, end,
                type, masterId, null);
        dataBus.taskRepository().save(subTask);

        project.addTask(subTask);
        dataBus.projectRepository().save(project);

        dataBus.teamService().notifyTeam(project.getTeamId(), NotifyConstant.NOTIFY_CREATE_SUBTASK, subTask);
        return ResponseUtil.send(ResponseState.OK);

    }

    @Override
    public Response updateTask(Long id, String name, String content, Long ownerId, Long requesterId, Date start, Date end,
                               Integer type, Long masterId, Integer complexity) throws Exception {
        Optional<Task> taskOptional = dataBus.taskRepository().findById(id);
        if (taskOptional.isEmpty()) {
            return ResponseUtil.sendErrorMessage(CommonConstant.TASK_NOT_EXIST);
        }
        Task task = taskOptional.get();
        Optional<Project> projectOptional = dataBus.projectRepository().findById(task.getProjectId());
        Project project = projectOptional.get();

        if (!name.isBlank()) {
            task.setName(name);
        }
        if (!content.isBlank()) {
            task.setContent(content);
        }
        if (ownerId != null) {
            task.setOwnerId(ownerId);
        }
        if (requesterId != null) {
            task.setRequesterId(requesterId);
        }
        if (start != null) {
            task.setStart(start);
        }
        if (end != null) {
            task.setEnd(end);
        }
        if (type != null) {
            task.setType(type);
        }
        if (masterId != null) {
            task.setMasterId(masterId);
        }
        if (complexity != null) {
            task.setComplexity(complexity);
        }

        dataBus.taskRepository().save(task);

        dataBus.teamService().notifyTeam(project.getTeamId(), NotifyConstant.NOTIFY_UPDATE_TASK, task);
        return ResponseUtil.send(ResponseState.OK);
    }

    @Override
    public Response updateTaskStatus(Long id, TaskStatus taskStatus) throws Exception {
        Optional<Task> taskOptional = dataBus.taskRepository().findById(id);
        if (taskOptional.isEmpty()) {
            return ResponseUtil.sendErrorMessage(CommonConstant.TASK_NOT_EXIST);
        }
        Task task = taskOptional.get();
        task.setStatus(taskStatus.status);
        dataBus.taskRepository().save(task);

        Optional<Project> project = dataBus.projectRepository().findById(task.getProjectId());
        if (project.isPresent()){
            dataBus.teamService().notifyTeam(project.get().getTeamId(), NotifyConstant.NOTIFY_UPDATE_TASK, task);
        }
        return ResponseUtil.send(ResponseState.OK);
    }

    @Override
    public void deleteSubTask(Task subTask) throws Exception {

        Project project = dataBus.projectRepository().findById(subTask.getProjectId()).get();
        project.deleteTask(subTask.getId());
        dataBus.projectRepository().save(project);

        dataBus.taskRepository().delete(subTask);

        dataBus.teamService().notifyTeam(project.getTeamId(), NotifyConstant.NOTIFY_DELETE_SUBTASK, subTask.getId());
    }

    @Override
    public void deleteTask(Task task) throws Exception {

        Project project = dataBus.projectRepository().findById(task.getProjectId()).get();
        project.deleteTask(task.getId());
        dataBus.projectRepository().save(project);

        List<Task> subTaskList = dataBus.taskRepository().findAllByMasterId(task.getId());
        for (Task subTask : subTaskList) {
            deleteSubTask(subTask);
        }
        dataBus.taskRepository().delete(task);

        dataBus.teamService().notifyTeam(project.getTeamId(), NotifyConstant.NOTIFY_DELETE_TASK, task.getId());
    }

    @Override
    public Response deleteTask(Long taskId) throws Exception {
        Optional<Task> taskOptional = dataBus.taskRepository().findById(taskId);
        if (taskOptional.isEmpty()) {
            return ResponseUtil.sendErrorMessage(CommonConstant.TASK_NOT_EXIST);
        }
        Task task = taskOptional.get();
        int type = task.getType();
        if (TaskType.SubTask.getType() == type) {
            deleteSubTask(task);
            return ResponseUtil.sendBody(ResponseState.OK, taskId);
        }
        deleteTask(task);
        return ResponseUtil.send(ResponseState.OK);
    }

    @Override
    public Response getComments(ChannelSession session, Long taskId) throws ErrorMessageException {
        Optional<Task> taskOptional = dataBus.taskRepository().findById(taskId);
        if (taskOptional.isEmpty()){
            ResponseUtil.sendErrorMessage(CommonConstant.TASK_NOT_EXIST);
        }
        List<TaskComment> taskComments = dataBus.taskCommentRepository().findAllByTaskId(taskId);
        return ResponseUtil.sendBody(ResponseState.OK, taskComments);
    }

    @Override
    public Response addComment(ChannelSession session, Long taskId, String content) throws ErrorMessageException {
        Optional<Task> taskOptional = dataBus.taskRepository().findById(taskId);
        if (taskOptional.isEmpty()){
            ResponseUtil.sendErrorMessage(CommonConstant.TEAM_NOT_EXIST);
        }
        TaskComment taskComment = new TaskComment(taskId, content, session.getUserId());
        dataBus.taskCommentRepository().save(taskComment);

        Optional<Project> project = dataBus.projectRepository().findById(taskOptional.get().getProjectId());
        if (project.isPresent()){
            dataBus.teamService().notifyTeam(project.get().getTeamId(), NotifyConstant.NOTIFY_ADD_COMMENT, taskComment);
        }
        return ResponseUtil.send(ResponseState.OK);
    }
}
