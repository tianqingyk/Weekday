import { friendApply, friendReply } from "../action/friendAction.js";
import { create } from "../component/create.js";
import { icon } from "../component/icon.js";

let friendApplyId = null,
    apply = $("#friend-apply"),
    friend_apply = $("#friend-apply-list");

$(document).ready(() => {
    // friend reply
    friend_apply.delegate('#reply-yes', 'click', function () {
        friendApplyId = $(this).attr('friendapplyid')
        friendReply(true, friendApplyId)
        apply.hide()
    })
    friend_apply.delegate('#reply-no', 'click', function () {
        friendApplyId = $(this).attr('friendapplyid')
        friendReply(false, friendApplyId)
        apply.hide()
    })
    $('#search-list').delegate('#btn-add-user', 'click', function () {
        let id = $(this).parent().attr('userid')
        friendApply(id)
        $('#search-list').hide()
    })
})

let addFriendIcon = new Vue({
    el: '#btnAddFriend',
    components: {
        'Icon': icon
    }
})

new Vue({
    el: '#add-user-div',
    components: {
        'Add': create
    }
})

new Vue({
    el: '#btnSearchUser',
    components: {
        'Icon': icon
    }
})

new Vue({
    el: '#search-user-div',
    components: {
        'Add': create
    }
})

export { addFriendIcon }