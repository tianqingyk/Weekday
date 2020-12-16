import { deleteProjectByTeamId, saveProject, updateProjectInfo } from "./projectRepository.js";
import { subtaskArray, taskArray } from "./taskRepository.js";
import { projectArray } from "./projectRepository.js";

let teamArray
let team, member, userId, selectTeam, username, check = false

function saveTeamInfo(body) {
    teamArray = []
    body.forEach(element => {
        teamArray.push(element)
        if (element.projects.length !== 0) {
            saveProject(element.projects)
        }
    })
    sessionStorage.setItem('team', JSON.stringify(teamArray))
    sessionStorage.setItem('project', JSON.stringify(projectArray))
    sessionStorage.setItem('task', JSON.stringify(taskArray))
    sessionStorage.setItem('subtask', JSON.stringify(subtaskArray))
}

function getAllTeam() {
    team = JSON.parse(sessionStorage.getItem('team'))
    return team
}

function getTeamById(id) {
    selectTeam = null
    team = getAllTeam()
    team.forEach(element => {
        if (element.teamId === Number(id)) {
            selectTeam = element
        }
    })
    return selectTeam
}

function checkTeam(id) {
    check = false
    let team = getAllTeam()
    if (team === null) {
        return check
    }
    team.forEach(element => {
        if (element.teamId === Number(id)) {
            check = true
        }
    })
    return check
}

function checkProjectInTeam(projectId, teamId) {
    check = false
    team = getAllTeam()
    team.forEach(element => {
        if (element.teamId === teamId) {
            element.projects.forEach(item => {
               if (item.projectId === projectId) {
                    check = true
               }
            })
        }
    })
    return check
}

function updateTeamInfo(content, title) {
    let team = getAllTeam()
    let id = Number(content.teamId)
    if (title === 'team') {
        if (checkTeam(id)) {
            team = team.filter(element => element.teamId !== id)
        }
        team.push(content)
        content.projects.forEach(element => {
            updateProjectInfo(element, 'team')
        })
    }
    if (title === 'project') {
        let projectId = Number(content.projectId)
        for (let i = 0; i < team.length; i++) {
            if (team[i].teamId === id) {
                if (checkProjectInTeam(projectId, id)) {
                    team[i].projects = team[i].projects.filter(item => item.projectId !== projectId)
                }
                team[i].projects.push(content)
            }
        }
    }
    if (title === 'deleteProject') {
        let projectId = Number(content.projectId)
        for (let i = 0; i < team.length; i++) {
            if (team[i].teamId === id) {
                team[i].projects = team[i].projects.filter(element => element !== projectId)
            }
        }
    }
    sessionStorage.setItem('team', JSON.stringify(team))
}

function deleteTeamById(id) {
    team = getAllTeam()
    team = team.filter(element => element.teamId !== Number(id))
    deleteProjectByTeamId(id)
    sessionStorage.setItem('team', JSON.stringify(team))
}

function getMemberByTeamId(id) {
    member = []
    team = getAllTeam()
    team.forEach(element => {
        if (element.teamId === Number(id)) {
            member = element.members
        }
    })
    return member
}

function getMemberByName(name, teamId) {
    userId = ''
    team = getAllTeam()
    team.forEach(element => {
        if (element.teamId === Number(teamId)) {
            element.members.forEach(member => {
                if (member.name === name.toString()) {
                    userId = member.id
                }
            })
        }
    })
    return userId
}

function getMemberById(teamId, id) {
    username = ''
    team = getAllTeam()
    team.forEach(element => {
        if (element.teamId === Number(teamId)) {
            element.members.forEach(member => {
                if (member.id === Number(id)) {
                    username = member.name
                }
            })
        }
    })
    return username
}
// not use
function deleteMemberById(teamId, memberId) {
    team = getAllTeam()
    team.forEach(element => {
        if (element.teamId === Number(teamId)) {
            member = element.members
            element.members = member.filter(element => element.id !== Number(memberId))
        }
    })
    sessionStorage.setItem('team', JSON.stringify(team))
}
// addTeam, addTeamMember, updateTeam has been refactor to updateTeamInfo
function addTeam(content) {
    team = getAllTeam()
    team.push(content)
    sessionStorage.setItem('team', JSON.stringify(team))
}

function addTeamMember(content) {
    team = getAllTeam()
    team.forEach(element => {
        if (element.teamId === Number(content.teamId)) {
            element.members = content.members
        }
    })
    sessionStorage.setItem('team', JSON.stringify(team))
}

function updateTeam(content) {
    let teamId = content.teamId
    team = getAllTeam()
    for (let i = 0; i < team.length; i++) {
        if (team[i].teamId === Number(teamId)) {
            team[i].projects.push(content)
        }
    }
    sessionStorage.setItem('team', JSON.stringify(team))
}

function changeTeamOwner(teamId, memberId) {
    team = getAllTeam()
    for (let i = 0; i < team.length; i++) {
        if (team[i].teamId === Number(teamId)) {
            team[i].ownerId = Number(memberId)
        }
    }
    sessionStorage.setItem('team', JSON.stringify(team))
}

export { saveTeamInfo, checkTeam }
export { updateTeamInfo, changeTeamOwner }
export { getAllTeam, getTeamById }
export { getMemberByTeamId, getMemberByName, getMemberById }
export { deleteTeamById, deleteMemberById }