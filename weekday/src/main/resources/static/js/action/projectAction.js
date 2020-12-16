import { socket_send } from "../net/socketConnect.js";
import { checkMemberId } from "../common/commonService.js";

function createProject(id, name) {
    let message = {"cmd": "createProject", "teamId": id, "name": name.toString()}
    socket_send(message);
}

function updateProject(id) {
    let project = $('#update-project')
    let message = {
        "cmd": "updateProject", "projectId": id, "name": project.find('#name').val(),
        "description": project.find('#description').val()
    }
    socket_send(message)
}

function deleteProject(id) {
    let message = {"cmd": "deleteProject", "projectId": id}
    socket_send(message)
}

function createTask(projectId) {
    let owner = $('#task-owner').val()
    let requester = $('#task-requester').val()
    let teamId = $('#member-list').attr('teamid')
    owner = checkMemberId(owner, teamId)
    requester = checkMemberId(requester, teamId)
    let message = {
        "cmd": "createTask", "projectId": projectId.toString(), "name": $("#task-name").val(),
        "content": $("#task-content").val(), "ownerId": owner, "requesterId": requester,
        "start": $("#task-start").val(), "end": $("#task-end").val(), "complexity": $("#task-complex").val()
    }
    socket_send(message)
}

function updateTask(id, teamId) {
    let table = $('#update-table')
    let owner = table.find('#owner').val()
    owner = checkMemberId(owner, teamId)
    let message = {
        "cmd": "updateTask", "taskId": id, "name": table.find('#name').val(), "content": table.find('#content').val(),
        "ownerId": owner, "start": table.find('#start').val(), "end": table.find('#end').val(),
        "complexity": table.find('#complexity').val()
    }
    socket_send(message)
}

function addComment(comment) {
    let id = $('#show-comment').attr('taskid')
    let message = {"cmd": "addComment", "taskId": id, "content": comment.toString()}
    socket_send(message)
}

function getComment(id) {
    let message = {"cmd": "getComments", "taskId": id.toString()}
    socket_send(message)
}

function updateTaskStatus(id, content) {
    let status = Number(content) + 1
    let message = {"cmd": "updateTaskStatus", "taskId": id.toString(), "status": status.toString()}
    socket_send(message)
}

function createSubTask(id) {
    let task = $('#sub-table')
    let owner = task.find('#owner').val()
    let teamId = $('#member-list').attr('teamid')
    owner = checkMemberId(owner, teamId)
    let message = {
        "cmd": "createSubTask", "masterId": id.toString(), "name": task.find("#name").val(),
        "ownerId": owner, "start": task.find('#start').val(), "end": task.find('#end').val()
    }
    socket_send(message)
}

function deleteTask(id) {
    let message = {"cmd": "deleteTask", "taskId": id}
    socket_send(message)
}

export { createProject, createTask, createSubTask }
export { updateProject, updateTask, addComment, getComment, updateTaskStatus }
export { deleteProject, deleteTask }