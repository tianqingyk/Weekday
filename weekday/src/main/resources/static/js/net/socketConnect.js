import { getCmd, response_message } from "../test/test-netty.js"
import { responseHandler } from "../handler/responseHandler.js";
import { errorHandler } from "../handler/errorHandler.js";
import { thirdParty } from "../action/loginAction.js";
import { resetPassword } from "../common/common.js";

let socket;

if (!window.WebSocket) {
    window.WebSocket = window.MozWebSocket;
}

if (window.WebSocket) {
    socket = new WebSocket("ws://127.0.0.1:8888/ws");
    console.log(socket);
} else {
    alert("Not support WebSocket");
}

socket.onopen = on_open;

socket.onmessage = on_message;

socket.onclose = on_close;

socket.onerror = on_error;

function on_message(event) {
    let data = event.data;
    response_message(data);
    let content = JSON.parse(data);
    let response = new responseHandler(content.cmd, content.success, content.message, content.body)
    errorHandler(response.success, response.message)
    Reflect.apply(Reflect.get(response, response.cmd), undefined,
        [response.success, response.body])
}

function on_open() {
    console.log("WebSocket Connected!");
    thirdParty()
    getCmd()
    resetPassword()
}

function on_close() {
    console.log("WebSocket Closed!");
}

function on_error(event) {
    console.log("Error: " + event.data);
}

function socket_send(message) {
    if (!window.WebSocket) {
        return;
    }
    if (socket.readyState === WebSocket.OPEN) {
        console.log(JSON.stringify(message));
        socket.send(JSON.stringify(message));
        return true;
    } else {
        alert("WebSocket didn't connect!");
    }
}


export { socket, socket_send };