import { socket_send } from "../net/socketConnect.js";

function chat(userid) {
    let message = {"cmd": "chat", "userId": userid.toString(), "type": "1", "message": $("#input-message").val()};
    socket_send(message);
}

function teamChat(teamId) {
    let message = {"cmd": "teamChat", "teamId": teamId.toString(), "type": "1", "message": $("#input-message").val()};
    socket_send(message);
}

export { chat, teamChat }