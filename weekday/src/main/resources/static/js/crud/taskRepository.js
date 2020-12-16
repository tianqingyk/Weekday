import { updateProjectInfo } from "./projectRepository.js";

let taskArray = [], subtaskArray = []
let task, subtask, searchTask, flag

function saveTask(task) {
    task.forEach(element => {
        if (element.type === 0) {
            taskArray.push(element)
        }
        if (element.type === 1) {
            subtaskArray.push(element)
        }
    })
}

function getAllTask() {
    task = JSON.parse(sessionStorage.getItem('task'))
    return task
}

function getAllSubTask() {
    subtask = JSON.parse(sessionStorage.getItem('subtask'))
    return subtask
}

function getTaskById(id) {
    searchTask = null
    task = getAllTask()
    task.forEach(element => {
        if (element.id === Number(id)) {
            searchTask = element
        }
    })
    return searchTask
}

function getTaskByProjectId(id) {
    searchTask = []
    task = getAllTask()
    task.forEach(element => {
        if (element.projectId === Number(id)) {
            searchTask.push(element)
        }
    })
    return searchTask
}

function getSubtaskById(id) {
    searchTask = null
    subtask = getAllSubTask()
    subtask.forEach(element => {
        if (element.id === Number(id)) {
            searchTask = element
        }
    })
    return searchTask
}

function getSubtaskByMasterId(id) {
    searchTask = []
    subtask = getAllSubTask()
    subtask.forEach(element => {
        if (element.masterId === Number(id)) {
            searchTask.push(element)
        }
    })
    return searchTask
}

// create/update task/subtask
function updateTaskInfo(content) {
    let taskId = content.id
    if (content.type === 0) {
        task = getAllTask()
        if (checkTask(taskId, 0)) {
            task = task.filter(element => element.id !== taskId)
        }
        task.push(content)
        updateProjectInfo(content, 'task')
        sessionStorage.setItem('task', JSON.stringify(task))
    }
    if (content.type === 1) {
        subtask = getAllSubTask()
        if (checkTask(taskId, 1)) {
            subtask = subtask.filter(element => element.id !== taskId)
        }
        subtask.push(content)
        updateProjectInfo(content, 'task')
        sessionStorage.setItem('subtask', JSON.stringify(subtask))
    }
}

// delete task/subtask
function deleteTaskById(id, type) {
    if (type === 'master') {
        let taskNew = getAllTask()
        taskNew = taskNew.filter(element => element.id !== Number(id))
        updateProjectInfo(getTaskById(id), 'deleteTask')
        sessionStorage.setItem('task', JSON.stringify(taskNew))
    }
    if (type === 'sub') {
        let subtaskNew = getAllSubTask()
        subtaskNew = subtaskNew.filter(element => element.id !== Number(id))
        updateProjectInfo(getSubtaskById(id), 'deleteTask')
        sessionStorage.setItem('subtask', JSON.stringify(subtaskNew))
    }
}

// delete team/project => delete task
function deleteTaskByProjectId(id) {
    task = getAllTask()
    task.forEach(element => {
        if (element.projectId === id) {
            deleteSubtaskByMasterId(element.id)
        }
    })
    task = task.filter(element => element.projectId !== id)
    sessionStorage.setItem('task', JSON.stringify(task))
}

// delete team/project/task => delete subtask
function deleteSubtaskByMasterId(id) {
    subtask = getAllSubTask()
    subtask.filter(element => element.masterId !== id)
    sessionStorage.setItem('subtask', JSON.stringify(subtask))
}

function checkTask(taskId, type) {
    flag = false
    if (type === 0) {
        task = getAllTask()
        task.forEach(element => {
            if (element.id === taskId) {
                flag = true
            }
        })
    }
    if (type === 1) {
        subtask = getAllSubTask()
        subtask.forEach(element => {
            if (element.id === taskId) {
                flag = true
            }
        })
    }
    return flag
}

export { saveTask, getAllTask, getAllSubTask, getTaskById, getTaskByProjectId, getSubtaskByMasterId, getSubtaskById }
export { updateTaskInfo, deleteTaskById, deleteTaskByProjectId, deleteSubtaskByMasterId }
export { taskArray, subtaskArray }