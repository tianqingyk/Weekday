import { userInfo } from "../service/userInfoService.js";

let user = null

function saveUser(user) {
    sessionStorage.setItem("user", JSON.stringify(user))
}

function getUser() {
    user = JSON.parse(sessionStorage.getItem("user"))
    return user
}
// not use
function updateUserInfo(message) {
    let user = JSON.parse(sessionStorage.getItem("user"))
    if (message.name !== null) {
        user.name = message.name
    }
    if (message.email !== null) {
        user.email = message.email
    }
    if (message.phonenum !== null) {
        user.phonenum = message.phonenum
    }
    if (message.birthday !== null) {
        user.birthday = message.birthday
    }
    if (message.label !== null) {
        user.label = message.label
    }
    sessionStorage.setItem('user', JSON.stringify(user))
    userInfo()
}

export { saveUser, updateUserInfo, getUser }