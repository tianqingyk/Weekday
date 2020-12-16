import { logInfo, getCorrectDate } from "../common/commonService.js";
import { getUser, saveUser } from "../crud/userRepository.js";

function userInfoService(success, body) {
    if (success) {
        saveUser(body)
        userInfo();
        logInfo('Message: user-info', body)
    }
}

function userInfo() {
    $("#user-info").fadeIn();
    let user = getUser()
    $("#show-username").empty().append(user.username);
    $("#show-name").empty().append(user.name);
    $("#user-avatar").empty().attr("src", user.avatar);
    $("#show-label").empty().append(user.label);
    $("#show-email").empty().append(user.email);
    if (user.phonenum !== undefined) {
        let phone = user.phonenum.toString();
        if (phone.length === 10) {
            $("#show-phone").empty().append(phone.substr(0, 3) + '-' + phone.substr(3, 3) + '-'
                + phone.substr(6));
        } else if (phone.length === 11) {
            $("#show-phone").empty().append(phone.substr(0, 3) + '-' + phone.substr(3, 4) + '-'
                + phone.substr(7));
        } else {
            $("#show-phone").empty().append(phone);
        }
    }
    let date = new Date(user.birthday);
    $("#show-birthday").empty().append(getCorrectDate(date));
    $("#show-zone").empty().append('UTC' + (0 - new Date().getTimezoneOffset() / 60));
}

export { userInfoService, userInfo }