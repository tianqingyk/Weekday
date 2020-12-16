import { logInfo } from "../common/commonService.js";
import { getFriendById } from "../crud/friendRepository.js";
import { getUser } from "../crud/userRepository.js";
import { getTeamById } from "../crud/teamRepository.js";

function chatService(success, body) {
    if (success) {
        if (body.length !== 0) {
            $("#user-manage").hide();
            $("#project-manage").hide();
            $("#chat-page").fadeIn();
            let owner = body.owner;
            let friend = getFriendById(owner)
            let friendAvatar = friend.avatar
            $("#chat-main-user").attr("src", friendAvatar);
            $("#chat-name").empty().append(friend.name).attr("userid", owner);
            let message = "<div class='message-div'>" +
                "<img id=\"image1\" class=\"img-l\" src=" + friendAvatar + " alt>" +
                "<p class=\"message-receive\">" + body.message + "</p></div>";
            $("#chat-message").append(message);
        }
        logInfo("Message: chat-message", body)
    }
}

function teamChatService(success, body) {
    if (success) {
        let user = getUser()
        if (user.id !== body.owner) {
            if (body.length !== 0) {
                $("#user-manage").hide();
                $("#project-manage").hide();
                $("#chat-page").fadeIn();
                let teamId = body.param1;
                let team = getTeamById(teamId)
                $("#chat-name").attr("teamid", teamId);
                $('#chat-name').empty().append(team.name)
                let message = "<div class='message-div'>" +
                    "<img id=\"image1\" class=\"img-l\" src=\"/static/images/head.png\" alt>" +
                    "<p class=\"message-receive\">" + body.message + "</p></div>";
                $("#chat-message").append(message);
            }
            logInfo('Message: team-chat', body)
        }
    }
}

export { chatService, teamChatService }