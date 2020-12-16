import { chat, teamChat } from "../action/chatAction.js";
import { getUser } from "../crud/userRepository.js";
import { icon } from "../component/icon.js";

let userId = null,
    teamId = null,
    input_message = $("#input-message"),
    chat_name = $('#chat-name'),
    chat_avatar = $('#chat-main-user'),
    team_list = $('#team-list'),
    member_list = $('#member-list');
let user = getUser()

$(document).ready(function () {
    // chat send message
    $('#btn-chat').click(() => {
        userId = chat_name.attr("userid");
        teamId = chat_name.attr('teamid');
        message_send(userId, teamId, user.avatar);
    });
})

$(document).ready(() => {
    // chat input
    $("#input-message").bind('input propertychange', function () {
        $('#btn-chat').attr("disabled", false);
    });
    // friend chat
    $("#friend-list").delegate('dt div', 'click', function () {
        let chat_username = $(this).text();
        let chat_avatar_url = $(this).children().attr("src");
        chat_name.empty().append(chat_username);
        chat_name.attr("teamid", "");
        chat_name.attr("userid", $(this).children().next().attr("userid"));
        chat_avatar.empty().attr("src", chat_avatar_url);
        chatViewChange();
    });
    // member chat
    member_list.delegate('#btnMemberChat', 'click', function () {
        let userId = $(this).attr("userid")
        if (user.id === userId) {
            return
        }
        let chat_username = $(this).text();
        let chat_avatar_url = $(this).parent().children().attr("src");
        chat_name.empty().append(chat_username);
        chat_name.attr("teamid", "");
        chat_name.attr("userid", userId);
        chat_avatar.empty().attr("src", chat_avatar_url);
        chatViewChange();
    });
    // team chat
    team_list.delegate('#btnTeamChat', 'click', function () {
        let chat_username = $(this).prev().prev().prev().text().toString();
        chat_name.empty().append(chat_username.substring(3))
        chat_name.attr("userid", "");
        chat_name.attr("teamid", $(this).parent().attr("teamid"));
        chatViewChange();
    });
});

function message_send(userId, teamId, avatar) {
    let message = "<div class='message-div'>" +
        "<img id=\"image1\" class=\"img-r\" src='" + avatar + "'alt>" +
        "<p class=\"message-send\">" + input_message.val() + "</p></div>";
    $("#chat-message").append(message);
    if (userId.length !== 0) {
        chat(userId);
    } else {
        teamChat(teamId);
    }
    input_message.val('');
}

function chatViewChange() {
    $("#user-manage").hide();
    $("#project-manage").hide();
    $('#chat-page').fadeIn();
}