import { teamViewChange } from "./team.js";
import { create } from "../component/create.js";
import { deleteProject, updateProject } from "../action/projectAction.js";
import { projectInfo } from "../service/projectService.js";

let team_list = $('#team-list'),
    projectId = null;

$(document).ready(function () {
    // show create project div
    team_list.delegate('#btnCreateProject', 'click', function () {
        $(this).attr('id', 'btnCloseCreateProject')
        $('#add-project-div').attr('teamid', $(this).parent().attr('teamid'))
        $("#add-project-div").fadeIn()
    })
    // hide create project div
    team_list.delegate('#btnCloseCreateProject', 'click', function () {
        $('#add-project-div').fadeOut()
        $(this).attr('id', 'btnCreateProject')
    })
    // delete project
    team_list.delegate('#btnDeleteProject', 'click', function () {
        let id = $(this).parent().attr('projectid')
        deleteProject(id)
        $('#project-manage').hide()
    })
    // show update project div
    $('#show-project-name').delegate('#btnUpdateProject', 'click', () => {
        $('#project-info').hide()
        $('#project-top').attr('class', 'container-fluid project-top-update')
        $('#update-project').fadeIn()
    })
    // update project
    $('#btn-update-project').click(function () {
        let id = $('#show-project-name').attr('projectid')
        $('#update-project').hide()
        $('#project-top').attr('class', 'container-fluid project-top')
        $('#project-info').fadeIn()
        updateProject(id)
    })
})

new Vue({
    el: '#add-project-div',
    components: {
        'Add': create
    }
})

$(document).ready(function () {
    // project show
    team_list.delegate('li div', 'click', function () {
        projectId = $(this).parent().attr('projectid')
        projectInfo(projectId)
        teamViewChange()
        $("#project-info").fadeIn()
        $("#task-info").fadeIn()
    })
})
