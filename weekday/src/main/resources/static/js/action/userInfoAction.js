import { socket_send } from "../net/socketConnect.js";

function getUserInfo() {
    let message = {"cmd": "getUserInfo"};
    socket_send(message);
}

function updateUser() {
    let message = {
        "cmd": "updateUser", "name": $("#edit-name").val(), "avatar": "",
        "email": $("#edit-email").val(), "phonenum": $("#edit-phone").val(), "birthday": $("#edit-birthday").val(),
        "label": $("#edit-label").val()
    };
    socket_send(message);
}

function updateUserAvatar(avatar) {
    let message = {"cmd": "updateUser", "avatar": avatar}
    socket_send(message);
}

export { getUserInfo, updateUser, updateUserAvatar }