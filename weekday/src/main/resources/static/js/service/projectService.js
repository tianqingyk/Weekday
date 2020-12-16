import { memberInfo } from "./teamService.js";
import { logInfo, setCurrentTeam, setTeamList } from "../common/commonService.js";
import { deleteProjectById, getProjectById, getProjectStatus, updateProjectInfo } from "../crud/projectRepository.js";
import { taskInfo } from "./taskService.js";

let show_project_name = $('#show-project-name')

function projectService(success, body) {
    if (success) {
        updateProjectInfo(body, 'project')
        setTeamList()
        projectInfo(body.projectId)
        logInfo('Message: create-project', body)
    }
}

function updateProjectService(success, body) {
    if (success) {
        updateProjectInfo(body, 'project')
        setTeamList()
        setCurrentTeam()
        logInfo('Message: update-project', body)
    }
}

function deleteProjectService(success, body) {
    if (success) {
        deleteProjectById(body)
        setTeamList()
        //setCurrentTeam()
        logInfo('Message: update-project', body)
    }
}

function projectInfo(projectId) {
    let temp_project = getProjectById(projectId)
    let status = getProjectStatus(projectId)
    let task = temp_project.tasks
    show_project_name.empty().append(temp_project.name
        + "<img id='btnUpdateProject' class='list-icon' src='/static/images/pencil-fill.svg' alt=''>")
    show_project_name.attr('projectid', projectId)
    $('#project-progress').css('width', status).empty().append(status)
    $('#show-project-description').empty().append(temp_project.description)
    memberInfo(temp_project.teamId)
    taskInfo(task, temp_project)
}

export { projectService, updateProjectService, deleteProjectService, projectInfo }
