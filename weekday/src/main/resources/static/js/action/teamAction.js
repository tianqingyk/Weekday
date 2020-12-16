import { socket_send } from "../net/socketConnect.js";

function getTeamInfo() {
    let message = {"cmd": "getTeamInfo"};
    socket_send(message);
}

function createTeam(name) {
    let message = {"cmd": "createTeam", "name": name.toString()};
    socket_send(message);
}
function deleteTeam(id) {
    let message = {"cmd": "deleteTeam", "teamId": id};
    socket_send(message);
}

function changeOwner(id) {
    let message = {
        "cmd": "changeOwner", "memberId": id.toString(),
        "teamId": $('#member-list').attr('teamid').toString()
    };
    socket_send(message);
}

function addMember(id) {
    let message = {
        "cmd": "addMember", "memberId": id.toString(),
        "teamId": $('#member-list').attr('teamid').toString()
    };
    socket_send(message);
}

function deleteMember(memberId) {
    let teamId = $('#member-list').attr('teamid').toString()
    let message = {"cmd": "deleteMember", "memberId": memberId, "teamId": teamId};
    socket_send(message);
}

export { getTeamInfo, createTeam, deleteTeam, changeOwner, addMember, deleteMember }