import { socket_send } from "../net/socketConnect.js";
import { getQueryString } from "../common/common.js";

new Vue({
    el: '#btn-login',
    methods: {
        login: function () {
            login()
        }
    }
})

function login() {
    let message = {"cmd": "login", "username": $("#login-username").val(), "password": $("#password").val()};
    socket_send(message);
}

function thirdParty() {
    let userId = getQueryString('userId');
    let code = getQueryString('code');
    if (userId !== null && code !== null) {
        let message = {"cmd": "loginByAuthority", "userId": userId, "code": code};
        socket_send(message);
    }
}

export { thirdParty }