import { friendApply, getUserListByName } from "../action/friendAction.js";
import { addMember, changeOwner, createTeam } from "../action/teamAction.js";
import { createProject, addComment } from "../action/projectAction.js";
import { addFriendIcon } from "../page/friend-bar.js";

let create = Vue.component('create', {
    template: `
        <div class="input-group">
        <input ref="input" type="text" class="form-control" :placeholder="cmd">
        <div class="input-group-append">
            <button v-on:click="add" class="btn btn-sm btn-primary">{{ title }}</button>
        </div>
        </div>`,
    props: {
        cmd: String,
        title: String
    },
    methods: {
        add: function () {
            let message = this.$refs.input.value
            if (this.cmd === 'add-friend') {
                friendApply(message)
                //console.log(addFriendIcon.check)
                $('#add-user-div').hide()
            }
            if (this.cmd === 'create-team') {
                createTeam(message)
                $("#team-member").fadeIn()
                //addTeamIcon.check = false
                $("#add-team-div").hide()
            }
            if (this.cmd === 'add-member') {
                addMember(message)
                //addMateIcon.check = false
                $("#add-member-div").hide()
            }
            if (this.cmd === 'change-owner') {
                changeOwner(message);
                //modifyOwnerIcon.check = false
                $('#change-owner-div').hide()
            }
            if (this.cmd === 'create-project') {
                createProject($('#add-project-div').attr('teamid'), message)
                $("#add-project-div").hide()
                $("#project-info").fadeIn()
            }
            if (this.cmd === 'add-comment') {
                //commentIcon.check = false
                $('#add-comment-div').hide()
                addComment(message)
            }
            if (this.cmd === 'search-user') {
                $('#search-user-div').hide()
                getUserListByName(message)
            }
        }
    }
})

export { create }