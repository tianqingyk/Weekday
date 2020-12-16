import { socket_send } from '../net/socketConnect.js';

// let commands = ["{\"cmd\": \"login\",\"username\":\"yangke\",\"password\":\"yangke\" }",
//     "{\"cmd\": \"login\",\"username\":\"zeyu\",\"password\":\"1\" }",
//     "{\"cmd\": \"login\",\"username\":\"Sun\",\"password\":\"SXY\" }",
//     "{\"cmd\": \"getUserInfo\"}",
//     "{\"cmd\": \"updateUser\", \"name\": \"\", \"avatar\": \"\", \"email\": \"\"}", "{\"cmd\": \"getTeamInfo\"}",
//     "{\"cmd\": \"createTeam\", \"name\": \"\"}", "{\"cmd\": \"addMember\", \"memberId\": \"\", \"teamId\": \"\"}",
//     "{\"cmd\": \"createProject\", \"teamId\": \"\", \"name\": \"\"}",
//     "{\"cmd\": \"createTask\", \"projectId\": \"\", \"name\": \"\", \"content\": \"\"}",
//     "{\"cmd\": \"updateTask\", \"taskId\": \"\", \"name\": \"\", \"content\": \"\"," +
//     " \"ownerId\":\"\",\"complexity\":\"\"}",
//     "{\"cmd\": \"getFriendList\"}",
//     "{\"cmd\": \"friendApply\", \"friendId\": \"\"}", "{\"cmd\": \"getFriendApplies\"}",
//     "{\"cmd\": \"friendReply\", \"acceptOrNot\": \"\", \"friendApplyId\": \"\"}",
//     "{\"cmd\": \"chat\", \"userId\": \"\", \"type\": \"\", \"message\": \"\"}",
//     "{\"cmd\": \"teamChat\", \"teamId\": \"\", \"type\": \"\", \"message\": \"\"}",
//     "{\"cmd\":\"deleteMember\", \"memberId\":\"\" ,\"teamId\":\"\"}",
//     "{\"cmd\":\"deleteTeam\", \"teamId\":\"2\" }",
//     "{\"cmd\":\"changeOwner\", \"memberId\":\"\" ,\"teamId\":\"\"}"];

let vue = new Vue({
    el: '#command',
    data: {
        shows: null
    }
})
// vue.shows = commands

$(document).ready(function () {
    $("#responseText").empty();
    $("form").on('submit', (e) => {
        e.preventDefault();
    });

    $("#command").delegate("a", "click", function () {
        $("#data-input").val($(this).text());
    });

    $("#btn-send").click(() => {
        send();
    });
});

function cmdList(body) {
    console.log(body)
}

function getCmd() {
    let message = {"cmd": "getCmd"}
    socket_send(message)
}

function getCmdService(success, body){
    vue.shows = body
}

function send() {
    let message = $("#data-input").val()
    socket_send(JSON.parse(message))
}

function response_message(event) {
    let json = JSON.parse(event)
    if (json.cmd === 'getCmd') {
        return
    }
    $("#responseText").append(event + '\n');
}

export { response_message, cmdList , getCmdService, getCmd};
