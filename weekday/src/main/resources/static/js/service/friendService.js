import { logInfo } from "../common/commonService.js";
import { getFriend, saveFriend } from "../crud/friendRepository.js";
import { friend_flag, workspace_flag } from "../page/nav-bar.js";

let friendArray = [];
let search_list = $('#search-list')

function friendService(success, body) {
    if (success) {
        if (body.length !== 0) {
            saveFriend(body)
            friendInfo();
            logInfo('Message: friend-info', body)
        }
    }
}

function friendApplyService(success, body) {
    if (success) {
        if (body.length !== 0) {
            friendApplyList(body);
            logInfo('Message: friend-apply', body)
        }
    }
}

function userListService(success, body) {
    if (success) {
        if (body.length !== 0) {
            searchList(body)
            search_list.fadeIn()
            logInfo('Message: search-list', body)
        }
    }
}

function friendInfo() {
    let list = $("#friend-list");
    list.empty();
    friendArray = getFriend()
    friendArray.forEach(element => {
        list.append("<dt><div class='chat-profile'>"
            + "<img class='chat-user-img' style='display:inline' src='" + element.avatar + "'>"
            + "<a userid='" + element.id + "' class='chat-name'>"
            + element.name + "</a></div></dt>");
    });
}

function friendApplyList(message) {
    let list = $("#friend-apply-list");
    list.empty();
    let applyList = message
    $('#workspace-panel').hide()
    $('#comment-panel').hide()
    $("#friend-panel").fadeIn();
    $("#main-right").attr("class", "col-lg-9 main-page");
    $("#friend-apply").fadeIn();
    for (let i = 0; i < applyList.length; i++) {
        let friendApplyId = applyList[i].friendApplyId;
        list.append("<li>" + applyList[i].user.name
            + "<br><button id='reply-yes' class='btn btn-outline-info btn-sm friend-button' "
            + "friendapplyid='" + friendApplyId + "'>Yes</button>"
            + "<button id='reply-no' class='btn btn-outline-danger btn-sm' style='margin-left:5px'"
            + "friendapplyid='" + friendApplyId + "'>No</button>" + "</li>");
    }
}

function searchList(list) {
    let users = list.content
    search_list.empty()
    console.log(users)
    for (let i = 0; i < users.length; i++) {
        search_list.append("<li userid='" + users[i].id +"'>" + users[i].name
            + "<button id='btn-add-user' class='btn btn-outline-info btn-sm friend-button'>Add</button></li>")
    }
}

export { friendService, friendApplyService, userListService }