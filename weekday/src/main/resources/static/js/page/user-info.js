import { webUploader } from "../upload.js";
import { updateUser, getUserInfo } from "../action/userInfoAction.js";

$(document).ready(function () {
    // show update div
    $("#btnEdit").click(() => {
        $("#user-info").hide();
        $("#edit-page").fadeIn();
    });
    // updateUser
    $("#btn-edit-info").click(() => {
        updateUser();
        $("#edit-page").hide();
        getUserInfo();
    });
    // avatar upload
    $("#user-avatar").on('click', () => {
        $("#user-info").hide();
        $("#upload-avatar").fadeIn();
        webUploader();
    });

})