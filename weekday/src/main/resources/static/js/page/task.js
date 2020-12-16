import {
    createSubTask,
    createTask,
    deleteTask,
    getComment,
    updateTask,
    updateTaskStatus
} from "../action/projectAction.js";
import { getMemberById, getMemberByTeamId } from "../crud/teamRepository.js";
import { getSubtaskById, getSubtaskByMasterId, getTaskById } from "../crud/taskRepository.js";
import { checkDate, checkMember, checkStatus, getCorrectDate } from "../common/commonService.js";
import { getProjectById } from "../crud/projectRepository.js";
import { create } from "../component/create.js";
import { icon } from "../component/icon.js";
import { workspace_flag } from "./nav-bar.js";

let projectId = null,
    main_right = $("#main-right"),
    updateTable = $('#update-table'),
    taskTable = $('#my-table')

$(document).ready(function () {
    // add task show input
    $("#btnAddTask").click(function () {
        $("#add-task").fadeIn();
        let team_id = $('#member-list').attr('teamid');
        addOwnerRequester(getMemberByTeamId(team_id));
        $("#task-info").hide();
    });
    // createTask
    $("#btn-add-task").click(() => {
        projectId = $("#show-project-name").attr("projectid");
        createTask(projectId);
        $("#add-task").hide();
        $("#task-info").fadeIn();
    });
    // hover
    taskTable.delegate('#btnTaskOperate', 'click', function () {
        $('#hover-show').fadeIn()
        let taskId = $(this).parent().parent().attr('taskid')
        $('#btnTaskUpdate').attr('taskid', taskId)
        $('#btn-delete-task').attr('taskid', taskId)
        let name = $(this).parent().text()
        $('#btnShowComment').attr('name', name).attr('taskid', taskId)
    })
    // show update task div
    $('#btnTaskUpdate').click(function () {
        $('#task-info').hide()
        $('#hover-show').fadeOut()
        $('#update-task').fadeIn()
        let taskId = $(this).attr('taskid')
        updateTaskTable(taskId)
    })
    // update task
    $('#btn-update-task').click(() => {
        let taskId = $('#update-table tr').attr('taskid')
        let teamId = $('#update-table tr').attr('teamid')
        updateTask(taskId, teamId)
        $('#update-task').hide()
        $("#task-info").fadeIn()
    })
    // update task status
    taskTable.delegate('#btnUpdateStatus', 'click', function () {
        let taskId = $(this).parent().parent().attr('taskid')
        let status = $(this).val()
        if (Number(status) < 2) {
            updateTaskStatus(taskId, status)
        }
    })
    // delete task
    $('#btn-delete-task').click(function () {
        let taskId = $(this).attr('taskid')
        deleteTask(taskId)
        $('#hover-show').fadeOut()
    })
    // show comment panel
    $("#btnShowComment").click(function () {
        let taskId = $(this).attr('taskid')
        $('#show-comment').attr('taskid', taskId)
        let name = $(this).attr('name')
        $('#comment-name').empty().append(name)
        //workspace_flag = false
        $("#workspace-panel").hide();
        $('#hover-show').fadeOut()
        main_right.attr("class", "col-lg-8 main-page");
        $("#comment-panel").fadeIn();
        getComment(taskId)
    })
    // hide comment panel
    $('#btnCloseComment').click(() => {
        $('#comment-panel').hide()
        $('#add-comment-div').hide()
        main_right.attr("class", "col-lg-11 main-page");
    })
    // show add sub task
    taskTable.delegate('#btnAddSubTask', 'click', function () {
        let taskId = $(this).parent().parent().attr('taskid')
        $(this).parent().parent().after(addSubTask(taskId))
    })
    // add sub task
    taskTable.delegate('#btn-add-subtask', 'click', function () {
        let taskId = $(this).parent().parent().attr('taskid')
        createSubTask(taskId)
        $('#addSubTable').remove()
    })
    // show sub task
    taskTable.delegate('#btnSubtask', 'click', function () {
        let taskId = $(this).parent().parent().attr('taskid')
        $(this).parent().parent().after(subTaskInfo(taskId))
        $(this).attr('src', '/static/images/caret-up-fill.svg').attr('id', 'btnCloseSubtask')
    })
    // hide sub task
    taskTable.delegate('#btnCloseSubtask', 'click', function () {
        $('#subTaskTable').remove()
        $(this).attr('src', '/static/images/caret-down-fill.svg').attr('id', 'btnSubtask')
    })
})

let commentIcon = new Vue({
    el: '#btnAddComment',
    components: {
        'Icon': icon
    }
})

new Vue({
    el: '#add-comment-div',
    components: {
        'Add': create
    }
})

$(document).ready(function () {
    // add task owner requester
    $('#owner-list').delegate('a', 'click', function () {
        $('#task-owner').val($(this).text());
    });
    $('#requester-list').delegate('a', 'click', function () {
        $('#task-requester').val($(this).text());
    });
    // task table search
    $('#my-input').on('keyup', function () {
        let value = $(this).val().toLowerCase();
        $("#my-table tr").filter(function () {
            $(this).toggle($(this).text().toLowerCase().indexOf(value) > -1)
        });
    });
})
// add task owner/requester select
function addOwnerRequester(members) {
    $("#owner-list").empty();
    $("#requester-list").empty();
    for (let i = 0; i < members.length; i++) {
        $('#owner-list').append('<a class="dropdown-item">' + members[i].name + '</a>');
        $('#requester-list').append('<a class="dropdown-item">' + members[i].name + '</a>');
    }
}
// update task/subtask
function updateTaskTable(id) {
    let task = getTaskById(id)
    if (task === null) {
        task = getSubtaskById(id)
    }
    let teamId = getProjectById(task.projectId).teamId
    let owner = getMemberById(teamId, task.ownerId)
    owner = addOwner(owner)
    let start = new Date(task.start)
    let end = new Date(task.end)
    updateTable.empty()
    updateTable.append(updateColumnByType(task, teamId, owner, start, end))
}
// different update table for task/subtask
function updateColumnByType(task, teamId, owner, start, end) {
    let type = task.type
    if (type === 0) {
        return "<tr taskid='" + task.id + "' teamid='" + teamId + "'>"
            + "<td><input id='name' class=\"form-control\" value='" + task.name + "'/></td>"
            + "<td><input id='content' class=\"form-control\" value='" + task.content + "'/></td>"
            + "<td><select id='owner' class='custom-select'>" + owner + "</select></td>"
            + "<td><input id='start' class=\"form-control\" type='date' value='" + getCorrectDate(start) + "'/></td>"
            + "<td><input id='end' class=\"form-control\" type='date' value='" + getCorrectDate(end) + "'/></td>"
            + "<td><select id='complexity' class='custom-select'>" + changeComplex(task.complexity) + "</select>"
            + "</td>"
            + "</tr>"
    }
    if (type === 1) {
        return "<tr taskid='" + task.id + "' teamid='" + teamId + "'>"
            + "<td><input id='name' class=\"form-control\" value='" + task.name + "'/></td>"
            + "<td></td>"
            + "<td><select id='owner' class='custom-select'>" + owner + "</select></td>"
            + "<td><input id='start' class=\"form-control\" type='date' value='" + getCorrectDate(start) + "'/></td>"
            + "<td><input id='end' class=\"form-control\" type='date' value='" + getCorrectDate(end) + "'/></td>"
            + "<td></td>"
            + "</tr>"
    }
}
// set complexity to select
function changeComplex(complex) {
    let row = "<option selected value='" + complex + "'>" + complex + " points</option>"
    for (let i = 1; i < 5; i++) {
        if (i === complex) {
            continue
        }
        let temp = "<option value='" + i + "'>" + i + " points</option>"
        row += temp
    }
    return row
}
// add subtask
function addSubTask(id) {
    return "<tr id='addSubTable'>"
        + "<td colspan='9'>"
        + "<table class='table table-sm table-hover'>"
        + "<thead>"
        + "<tr class='th-font'>"
        + "<th>Name</th>"
        + "<th>Owner</th>"
        + "<th>Start</th>"
        + "<th>End</th>"
        + "<th>Submit</th>"
        + "</tr>"
        + "</thead>"
        + "<tbody id='sub-table'>"
        + "<tr taskid='" + id + "'>"
        + "<td><input id='name' class=\"form-control\"/></td>"
        + "<td><select id='owner' class='custom-select'>" + addOwner(null) + "</select></td>"
        + "<td><input id='start' class=\"form-control\" type='date'/></td>"
        + "<td><input id='end' class=\"form-control\" type='date'/></td>"
        + "<td><button id='btn-add-subtask' class='btn btn-sm btn-primary'>Add</button></td>"
        + "</tr>"
        + "</tbody>"
        + "</table>"
        + "</td>"
        + "</tr>"
}
// owner select
function addOwner(name) {
    let team_id = $('#member-list').attr('teamid');
    let member = getMemberByTeamId(team_id)
    let temp = "<option selected>Please select an owner</option>"
    if (name !== null) {
        temp = "<option selected>" + name + "</option>"
    }
    for (let i = 0; i < member.length; i++) {
        if (member[i].name === name) {
            continue
        }
        let row = "<option>" + member[i].name + "</option>"
        temp += row
    }
    return temp
}
// show subtask table
function subTaskInfo(id) {
    let subtasks = getSubtaskByMasterId(id)
    let teamId = getProjectById(getTaskById(id).projectId).teamId
    let rows = "<tr id='subTaskTable'>"
        + "<td colspan='9'>"
        + "<table class='table table-sm table-hover'>"
        + "<thead>"
        + "<tr class='th-font'>"
        + "<th>Name</th>"
        + "<th>Owner</th>"
        + "<th>Start</th>"
        + "<th>End</th>"
        + "<th>Status</th>"
        + "</tr>"
        + "</thead>"
        + "<tbody id='subtask-table'>"
    for (let i = 0; i < subtasks.length; i++) {
        let owner = checkMember(teamId, subtasks[i].ownerId)
        let start = checkDate(subtasks[i].start)
        let end = checkDate(subtasks[i].end)
        let status = checkStatus(subtasks[i].status)
        let row = "<tr taskid='" + subtasks[i].id + "'>"
            + "<td>" + subtasks[i].name
            + "<img id='btnTaskOperate' class='list-icon' src='/static/images/list-ul.svg' alt=''>"
            + "</td>"
            + "<td>" + owner + "</td>"
            + "<td>" + start + "</td>"
            + "<td>" + end + "</td>"
            + "<td>" + status + "</td>"
        rows += row
    }
    let tail = "</tbody>"
        + "</table>"
        + "</td>"
        + "</tr>"
    rows += tail
    return rows
}
