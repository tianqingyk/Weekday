import { logInfo, setCurrentTeam, setTeamList } from "../common/commonService.js";
import { getUser } from "../crud/userRepository.js";
import {
    checkTeam, deleteTeamById,
    getAllTeam,
    getMemberByTeamId,
    saveTeamInfo, updateTeamInfo
} from "../crud/teamRepository.js";
import { getAllProject, getProjectByTeamId } from "../crud/projectRepository.js";
import { getAllTask } from "../crud/taskRepository.js";

let team_id = null, project
let user = null
let member_list = $('#member-list'),
    team_list = $("#team-list");

function teamService(success, body) {
    if (success) {
        team_list.empty();
        if (body.length !== 0) {
            saveTeamInfo(body)
            setTeamList()
            setCurrentTeam()
            logInfo('Message: team-info', getAllTeam())
            logInfo('Message: project-info', getAllProject())
            logInfo('Message: task-info', getAllTask())
        }
    }
}

function updateTeamService(success, body) {
    if (success) {
        if (checkTeam(body.teamId)) {
            updateTeamInfo(body, 'team')
            setCurrentTeam()
            logInfo('Message: update team success', getAllTeam())
        } else {
            updateTeamInfo(body, 'team')
            setTeamList()
            memberInfo(body.teamId)
            logInfo('Message: add team success', getAllTeam())
        }
    }
}

function deleteTeamService(success, body) {
    if (success) {
        deleteTeamById(body)
        $('#project-manage').fadeOut()
        setTeamList()
        logInfo('Message: delete team success', getAllTeam())
    }
}

function setTeamInfo(team) {
    for (let i = 0; i < team.length; i++) {
        teamInfo(team[i], team_list, i);
        team_id = team[i].teamId
        project = getProjectByTeamId(team_id)
        for (let j = 0; j < project.length; j++) {
            teamProject(project[j], team_list)
        }
    }
}

function teamInfo(team, list, i) {
    list.append("<div style='float: left;font-weight: bold;' teamid='" + team.teamId + "'>"
        + "<div style='display: inline'>" + (i + 1) + ". " + team.name + "</div>"
        + "<img id='btnCreateProject' class='list-icon' src='/static/images/folder-plus.svg' alt=''>"
        + "<img id='btnDeleteTeam' class='list-icon' src='/static/images/bookmark-dash.svg' alt=''>"
        + "<img id='btnTeamChat' class='list-icon' src='/static/images/chat-text.svg' alt=''>"
        + "</div><br>");
}

function teamProject(project, list) {
    list.append("<li style='margin-left: 20px' projectid='" + project.projectId + "'><div style='display: inline'>"
        + project.name + "</div>"
        + "<img id='btnDeleteProject' class='list-icon' src='/static/images/folder-minus.svg' alt=''>"
        + "</li>");
}

function getMemberInfoHeader(user) {
    return "<div class='member-profile'><dt>"
        + "<img class='chat-user-img' style='display:inline' src='" + user.avatar + "' alt=''>"
        + "<a id='btnMemberChat' userid='" + user.id + "' class='chat-name'>" + user.name + "</a>"
}

function memberInfo(id) {
    let temp_member = getMemberByTeamId(id);
    member_list.empty();
    member_list.attr('teamid', id);
    let tail = "</dt></div>"
    user = getUser()
    let self = getMemberInfoHeader(user) + tail
    member_list.append(self);
    temp_member.forEach((value, key) => {
        if (value.id !== user.id) {
            let member = getMemberInfoHeader(value)
                + "<div><img id='btnDeleteMember' src='/static/images/person-dash.svg' class='sm-icon' alt=''>"
                + "</div>" + tail
            member_list.append(member);
        }
    });
}

export {
    teamService,
    updateTeamService,
    deleteTeamService,
    memberInfo,
    teamInfo,
    teamProject,
    setTeamInfo
}