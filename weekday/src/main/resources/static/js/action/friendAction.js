import { socket_send } from "../net/socketConnect.js";

function getFriendList() {
    let message = {"cmd": "getFriendList"};
    socket_send(message);
}

function friendApply(userId) {
    let message = {"cmd": "friendApply", "friendId": userId.toString()};
    socket_send(message);
}

function getFriendApplies() {
    let message = {"cmd": "getFriendApplies"};
    socket_send(message);
}

function friendReply(flag, friendApplyId) {
    let message = {"cmd": "friendReply", "acceptOrNot": flag.toString(), "friendApplyId": friendApplyId.toString()}
    socket_send(message);
}

function getUserListByName(name) {
    let message = {"cmd": "getUserListByName", "name": name, "page": "0"}
    socket_send(message)
}

export { getFriendList, friendApply, getFriendApplies, friendReply, getUserListByName }