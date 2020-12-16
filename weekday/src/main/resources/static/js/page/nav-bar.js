let workspace_flag = false,
    friend_flag = false,
    main_right = $("#main-right"),
    chat_left = $("#chat-left"),
    chat_center = $("#chat-center");

function common(status) {
    if (status === 'show') {
        main_right.attr("class", "col-lg-9 main-page");
        chat_left.attr("class", "col-lg-2");
        chat_center.attr("class", "col-lg-8");
    } else {
        main_right.attr("class", "col-lg-11 main-page");
        chat_left.attr("class", "col-lg-3");
        chat_center.attr("class", "col-lg-6");
    }
}

function check(btn) {
    if (btn === 'workspace') {
        friend_flag = false;
        workspace_flag = workspace_flag === false;
        if (workspace_flag) {
            common('show')
            $("#friend-panel").hide();
            $("#user-manage").hide();
            $("#chat-page").hide();
            $("#workspace-panel").fadeIn();
            $("#project-manage").fadeIn();
            // $("#project-info").fadeIn();
            // $("#task-info").fadeIn();
        } else {
            $("#workspace-panel").hide();
            common('hide')
        }
    }
    if (btn === 'friend') {
        workspace_flag = false;
        friend_flag = friend_flag === false;
        if (friend_flag) {
            common('show')
            $("#friend-panel").fadeIn();
            $("#workspace-panel").hide();
        } else {
            $("#friend-panel").hide();
            common('hide')
        }
    }
    if (btn === 'user') {
        if (friend_flag || workspace_flag) {
            friend_flag = false;
            workspace_flag = false;
        }
        $('#edit-page').hide()
        $('#upload-avatar').hide()
        $("#project-manage").hide();
        $("#chat-page").hide();
        $("#user-manage").fadeIn();
        $('#user-info').fadeIn()
    }
}

let workspace = new Vue({
    el: '#btnWorkspace',
    methods: {
        check: function () {
            check('workspace')
        }
    }
})

let friend = new Vue({
    el: '#btnFriend',
    methods: {
        check: function () {
            check('friend')
        }
    }
})

let user = new Vue({
    el: '#btnUser',
    methods: {
        check: function () {
            check('user')
        }
    }
})

export { friend_flag, workspace_flag }