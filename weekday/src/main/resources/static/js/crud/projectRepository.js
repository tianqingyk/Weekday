import { deleteTaskByProjectId, saveTask, updateTaskInfo } from "./taskRepository.js";
import { updateTeamInfo } from "./teamRepository.js";

let projectArray = [], project, flag, status
let searchProject = []

function saveProject(projects) {
    projects.forEach(element => {
        projectArray.push(element)
        if (element.tasks.length !== 0) {
            saveTask(element.tasks)
        }
    })
}

function getAllProject() {
    project = JSON.parse(sessionStorage.getItem('project'))
    return project
}

function getProjectByTeamId(id) {
    searchProject = []
    project = getAllProject()
    searchProject = []
    project.forEach(element => {
        if (element.teamId === Number(id)) {
            searchProject.push(element)
        }
    })
    return searchProject
}

function getProjectById(id) {
    searchProject = null
    project = getAllProject()
    project.forEach(element => {
        if (element.projectId === Number(id)) {
            searchProject = element
        }
    })
    return searchProject
}

function getProjectStatus(id) {
    status = 0
    project = getProjectById(id)
    let count = 0, len = 0
    project.tasks.forEach(element => {
        if (element.type === 0) {
            len++
            if (element.status === 2) {
                count++
            }
        }
    })
    if (project.tasks.length !== 0) {
        status = Math.round(count / len * 100)
    }
    return status + "%"
}

// add/update project
function updateProjectInfo(content, title) {
    let projectId = Number(content.projectId)
    let project = getAllProject()
    // bottom to top
    if (title === 'task') {
        let taskId = Number(content.id)
        for (let i = 0; i < project.length; i++) {
            if (project[i].projectId === projectId) {
                if (checkTaskInProject(taskId, projectId)) {
                    project[i].tasks = project[i].tasks.filter(element => element.id !== taskId)
                }
                project[i].tasks.push(content)
                updateTeamInfo(project[i], 'project')
            }
        }
    }
    if (title === 'project') {
        if (checkProject(projectId)) {
            project = project.filter(element => element.projectId !== projectId)
        }
        project.push(content)
        updateTeamInfo(content, 'project')
    }
    if (title === 'deleteTask') {
        let taskId = Number(content.id)
        for (let i = 0; i < project.length; i++) {
            if (project[i].projectId === projectId) {
                project[i].tasks = project[i].tasks.filter(element => element.id !== taskId)
                updateTeamInfo(project[i], 'project')
            }
        }
    }
    // top to bottom
    if (title === 'team') {
        if (checkProject(projectId)) {
            project = project.filter(element => element.projectId !== projectId)
        }
        project.push(content)
        content.tasks.forEach(element => {
            updateTaskInfo(element)
        })
    }
    sessionStorage.setItem('project', JSON.stringify(project))
}

function deleteProjectById(id) {
    project = getAllProject()
    updateTeamInfo(getProjectById(id), 'deleteProject')
    deleteTaskByProjectId(id)
    project = project.filter(element => element.projectId !== Number(id))
    sessionStorage.setItem('project', JSON.stringify(project))
}

function deleteProjectByTeamId(id) {
    project = getAllProject()
    project.forEach(element => {
        if (element.teamId === id) {
            deleteTaskByProjectId(element.projectId)
        }
    })
    project = project.filter(element => element.teamId !== id)
    sessionStorage.setItem('project', JSON.stringify(project))
}

function checkProject(projectId) {
    flag = false
    project = getAllProject()
    project.forEach(element => {
        if (element.projectId === projectId) {
            flag = true
        }
    })
    return flag
}

function checkTaskInProject(taskId, projectId) {
    flag = false
    let project = getProjectById(projectId)
    project.tasks.forEach(element => {
        if (element.id === taskId) {
            flag = true
        }
    })
    return flag
}

export { saveProject, projectArray }
export { getAllProject, getProjectByTeamId, getProjectById, getProjectStatus }
export { updateProjectInfo, deleteProjectById, deleteProjectByTeamId }