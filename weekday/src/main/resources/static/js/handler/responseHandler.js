import { loginService } from "../service/loginService.js";
import { userInfoService } from "../service/userInfoService.js";
import { updateTeamService, deleteTeamService, teamService } from "../service/teamService.js";
import { deleteProjectService, projectService, updateProjectService } from "../service/projectService.js";
import { friendApplyService, friendService, userListService } from "../service/friendService.js";
import { chatService, teamChatService } from "../service/chatService.js";
import { getCmdService } from "../test/test-netty.js";
import { noDataLogInfo } from "../common/commonService.js";
import {
    commentService,
    deleteTaskService,
    subtaskService,
    taskService,
    updateTaskService
} from "../service/taskService.js";

class responseHandler {
    constructor(cmd, success, message, body) {
        this._cmd = cmd;
        this._success = success;
        this._message = message;
        this._body = body;
    }

    get cmd() {
        return this._cmd;
    }

    get success() {
        return this._success;
    }

    get message() {
        return this._message;
    }

    get body() {
        return this._body;
    }

    login(success, body) {
        loginService(success)
    }

    loginByAuthority(success, body) {
        loginService(success)
    }

    getUserInfo(success, body) {
        userInfoService(success, body)
    }

    getUserListByName(success, body) {
        userListService(success, body)
    }

    getTeamInfo(success, body) {
        teamService(success, body)
    }

    updateTeamInfo(success, body) {
        updateTeamService(success, body)
    }

    deleteTeam(success, body) {
        deleteTeamService(success, body)
    }

    createProjectNotify(success, body) {
        projectService(success, body)
    }

    updateProjectNotify(success, body) {
        updateProjectService(success, body)
    }

    deleteProjectNotify(success, body) {
        deleteProjectService(success, body)
    }

    createTaskNotify(success, body) {
        taskService(success, body)
    }

    updateTaskNotify(success, body) {
        updateTaskService(success, body)
    }

    addCommentNotify(success, body) {
        commentService(success, body, 'add')
    }

    getComments(success, body) {
        commentService(success, body, 'get')
    }

    deleteTaskNotify(success, body) {
        deleteTaskService(success, body, 'master')
    }

    createSubtaskNotify(success, body) {
        subtaskService(success, body)
    }

    deleteSubtaskNotify(success, body) {
        deleteTaskService(success, body, 'sub')
    }

    getFriendList(success, body) {
        friendService(success, body)
    }

    getFriendApplies(success, body) {
        friendApplyService(success, body)
    }

    chatNotify(success, body) {
        chatService(success, body)
    }

    teamChatNotify(success, body) {
        teamChatService(success, body)
    }

    getCmd(success, body) {
        getCmdService(success, body)
    }
    /*
     * empty reflect
     */
    updateUser(success, body) {
        noDataLogInfo('Message: user info update success')
    }

    createTeam(success, body) {
        // noDataLogInfo('Message: create team success')
    }

    addMember(success, body) {
        // noDataLogInfo('Message: add member success')
    }

    deleteMember(success, body) {
        // noDataLogInfo('Message: delete Member success')
    }

    changeOwner(success, body) {

    }

    createProject(success, body) {

    }

    updateProject(success, body) {

    }

    deleteProject(success, body) {

    }

    createTask(success, body) {

    }

    updateTask(success, body) {

    }

    updateTaskStatus(success, body) {

    }

    addComment(success, body) {

    }

    deleteTask(success, body) {

    }

    createSubTask(success, body) {

    }

    friendApply(success, body) {

    }

    friendReply(success, body) {

    }

    chat(success, body) {

    }

    teamChat(success, body) {

    }
}

export { responseHandler }