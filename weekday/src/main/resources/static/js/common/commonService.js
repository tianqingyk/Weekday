import { getUserInfo } from "../action/userInfoAction.js";
import { getTeamInfo } from "../action/teamAction.js";
import { getFriendApplies, getFriendList } from "../action/friendAction.js";
import { projectInfo } from "../service/projectService.js";
import { memberInfo, setTeamInfo } from "../service/teamService.js";
import { getAllTeam, getMemberById, getMemberByName } from "../crud/teamRepository.js";
import { icon } from "../component/icon.js";

let member_list = $('#member-list'),
    team_list = $("#team-list");
let array = []

function loginSuccess() {
    $("#login-page").hide();
    $("#main-page").fadeIn();
    $("#user-manage").fadeIn();
    getUserInfo();
    getTeamInfo();
    getFriendList();
    getFriendApplies();
}

function setSession() {
    sessionStorage.setItem('friend', JSON.stringify(array))
    sessionStorage.setItem('team', JSON.stringify(array))
    sessionStorage.setItem('project', JSON.stringify(array))
    sessionStorage.setItem('task', JSON.stringify(array))
    sessionStorage.setItem('subtask', JSON.stringify(array))
}

function setTeamList() {
    team_list.empty()
    setTeamInfo(getAllTeam())
    //setTeamListIcon()
}

function setTeamListIcon() {
    new Vue({
        el: '#btnCreateProject',
        components: {
            'Icon': icon
        }
    })

    new Vue({
        el: '#btnDeleteTeam',
        components: {
            'Icon': icon
        }
    })

    new Vue({
        el: '#btnTeamChat',
        components: {
            'Icon': icon
        }
    })

    new Vue({
        el: '#btnDeleteProject',
        components: {
            'Icon': icon
        }
    })
}

function setCurrentTeam() {
    let currentTeamId = member_list.attr('teamid');
    let currentProjectId = $('#show-project-name').attr('projectid');
    if (currentTeamId !== undefined && currentTeamId.length !== 0) {
        memberInfo(currentTeamId)
        if (currentProjectId !== undefined && currentProjectId.length !== 0) {
            projectInfo(currentProjectId)
        }
    }
}

function logInfo(title, message) {
    console.group(title)
    console.log(message)
    console.groupEnd()
}

function noDataLogInfo(title) {
    console.group(title)
    console.groupEnd()
}

function checkMember(teamId, content) {
    let name = ''
    if (content !== undefined) {
        name = getMemberById(teamId, content)
    }
    return name
}

function checkMemberId(name, teamId) {
    let id = ''
    if (name !== null || name !== "" || name.length === 0) {
        id = getMemberByName(name, teamId).toString()
    }
    return id
}

function checkDate(content) {
    let date = ''
    if (content !== undefined) {
        date = new Date(content)
        date = getCorrectDate(date)
    }
    return date
}

function checkComplex(content) {
    let complex = ''
    if (content !== undefined) {
        complex = getComplexColor(content)
    }
    return complex
}

function checkStatus(content) {
    let type, title
    if (content === 0) {
        type = 'btn-secondary'
        title = 'start'
    }
    if (content === 1) {
        type = 'btn-primary'
        title = 'finish'
    }
    if (content === 2) {
        type = 'btn-success'
        title = 'finished'
    }
    return "<button id='btnUpdateStatus' value='" + content +"' class='btn " + type + "'>" + title + "</button>"
}

function getCorrectDate(date) {
    let month = date.getMonth() + 1
    let day = date.getDate()
    return date.getFullYear() + '-' + dateAppendZero(month) + '-' + dateAppendZero(day);
}

function dateAppendZero(date) {
    if (date < 10)
        return "0" + "" + date
    return date
}

function getComplexColor(complex) {
    let type
    if (complex === 1) {
        type = 'btn-primary'
        return "<button class='btn " + type + "'>" + complex + " point</button>"
    }
    if (complex === 2) {
        type = 'btn-success'
    }
    if (complex === 3) {
        type = 'btn-warning'
    }
    if (complex === 4) {
        type = 'btn-danger'
    }
    return "<button class='btn " + type + "'>" + complex + " points</button>"
}

export { loginSuccess, setCurrentTeam, setTeamList, setSession }
export { getComplexColor, getCorrectDate, checkDate, checkMember, checkMemberId, checkComplex, checkStatus }
export { logInfo, noDataLogInfo }