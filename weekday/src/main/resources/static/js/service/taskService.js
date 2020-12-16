import { deleteTaskById, getTaskById, updateTaskInfo } from "../crud/taskRepository.js";
import { logInfo, setCurrentTeam, checkDate,
    checkMember, checkComplex, checkStatus
} from "../common/commonService.js";
import { getMemberById } from "../crud/teamRepository.js";
import { getProjectById } from "../crud/projectRepository.js";

let task_table = $('#my-table'),
    comment_list = $('#show-comment')
let start, end, complex, owner, requester, status

function taskService(success, body) {
    if (success) {
        updateTaskInfo(body)
        setCurrentTeam()
        logInfo("Message: create-task", body)
    }
}

function updateTaskService(success, body) {
    if (success) {
        updateTaskInfo(body)
        setCurrentTeam()
        logInfo("Message: update-task", body)
    }
}

function deleteTaskService(success, body, type) {
    if (success) {
        deleteTaskById(body, type)
        setCurrentTeam()
        logInfo("Message: delete-task", body)
    }
}

function subtaskService(success, body) {
    if (success) {
        updateTaskInfo(body)
        setCurrentTeam()
        logInfo("Message: create-subtask", body)
    }
}

function commentService(success, body, title) {
    if (success) {
        if (title === 'get') {
            comment_list.empty()
            if (body.length !== 0) {
                body.forEach(element => {
                    taskCommentInfo(element)
                })
            }
        }
        if (title === 'add') {
            taskCommentInfo(body)
        }
        logInfo("Message: task-comment", body)
    }
}

function taskInfo(task, project) {
    task_table.empty();
    for (let i = 0; i < task.length; i++) {
        if (task[i].type !== 1) {
            setTaskInit()
            start = checkDate(task[i].start)
            end = checkDate(task[i].end)
            owner = checkMember(project.teamId, task[i].ownerId)
            requester = checkMember(project.teamId, task[i].requesterId)
            complex = checkComplex(task[i].complexity)
            status = checkStatus(task[i].status)
            task_table.append("<tr taskid='" + task[i].id + "'>" +
                "<td>" + task[i].name +
                "<img id='btnTaskOperate' class='list-icon' src='/static/images/list-ul.svg' alt=''></td>" +
                "<td>" + task[i].content + "</td>" +
                "<td>" +
                "<img id='btnSubtask' src='/static/images/caret-down-fill.svg' alt=''>" +
                "<img id='btnAddSubTask' class='list-icon' src='/static/images/file-earmark-plus.svg' alt=''>" +
                "</td>" +
                "<td>" + owner + "</td>" +
                "<td>" + requester + "</td>" +
                "<td>" + start + "</td>" +
                "<td>" + end + "</td>" +
                "<td>" + complex + "</td>" +
                "<td>" + status + "</td>" +
                "</tr>");
        }
    }
}

function setTaskInit() {
    complex = ''
    owner = ''
    requester = ''
}

function taskCommentInfo(comment) {
    comment_list.append("<div class='card'>" + commentCard(comment) + "</div>")
}

function commentCard(comment) {
    let user = comment.userId
    let teamId = getProjectById(getTaskById(comment.taskId).projectId).teamId
    user = checkMember(teamId, user)
    let card = "<div class='card-body'><h5 class='card-title'>"
        + user + "</h5>"
        + "<p class='card-text'>" + comment.comment + "</p>"
        + "<div class='row comment-row'>"
        + "<div class='col-lg-6 text-center comment-border'>"
        + "<button class='btn btn-light comment-btn'>"
        + "<img class='comment-icon' src='/static/images/hand-thumbs-up.svg' alt=''>"
        + "Like</button></div>"
        + "<div class='col-lg-6 text-center'>"
        + "<button class='btn btn-light comment-btn'>"
        + "<img class='comment-icon' src='/static/images/reply.svg' alt=''>"
        + "Reply</button></div>"
        + "</div></div>"
    return card
}

export { taskService, updateTaskService, deleteTaskService, subtaskService, commentService, taskInfo }