import { create } from "../component/create.js";
import { deleteMember, deleteTeam } from "../action/teamAction.js";
import { memberInfo } from "../service/teamService.js";
import { icon } from "../component/icon.js";

let team_list = $('#team-list'),
    member_list = $('#member-list');

$(document).ready(function () {
    // deleteTeam
    team_list.delegate('#btnDeleteTeam', 'click', function () {
        let id = $(this).parent().attr('teamid');
        deleteTeam(id);
    })
    // deleteMember
    member_list.delegate('#btnDeleteMember', 'click', function () {
        let member_id = $(this).parent().prev().attr('userid');
        deleteMember(member_id);
    });
})

let addTeamIcon = new Vue({
    el: '#btnAddTeam',
    components: {
        'Icon': icon
    }
})

new Vue({
    el: '#add-team-div',
    components: {
        'Add': create
    }
})

let addMateIcon = new Vue({
    el: '#btnAddMember',
    components: {
        'Icon': icon
    }
})

new Vue({
    el: '#add-member-div',
    components: {
        'Add': create
    }
})

let modifyOwnerIcon = new Vue({
    el: '#btnChangeOwner',
    components: {
        'Icon': icon
    }
})

new Vue({
    el: '#change-owner-div',
    components: {
        'Add': create
    }
})

$(document).ready(function () {
    // get team member info
    team_list.delegate('div div', 'click', function () {
        let id = $(this).parent().attr('teamid');
        memberInfo(id);
        teamViewChange();
        $("#project-info").fadeOut();
        $("#task-info").fadeOut();
    });
})

function teamViewChange() {
    $('#add-task').hide();
    $('#update-task').hide()
    $('#update-project').hide()
    $("#chat-page").hide();
    $("#user-manage").hide();
    $('#project-top').attr('class', 'container-fluid project-top')
    $("#project-manage").fadeIn();
    $("#team-member").fadeIn();
}

export { teamViewChange }