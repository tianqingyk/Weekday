let icon = Vue.component('icon', {
    template: `<img @click="show" :src="'/static/images/' + title + '.svg'" alt="">`,
    props: {
        title: String,
        cmd: String,
        check: Boolean
    },
    methods: {
        show: function (e) {
            if (this.cmd === 'addTeam') {
                if (!this.check) {
                    $("#add-team-div").fadeIn()
                    this.check = true
                } else {
                    $('#add-team-div').fadeOut()
                    this.check = false
                }
            }
            if (this.cmd === 'addFriend') {
                if (!this.check) {
                    $("#add-user-div").fadeIn()
                    this.check = true
                } else {
                    $('#add-user-div').fadeOut()
                    this.check = false
                }
            }
            if (this.cmd === 'searchFriend') {
                if (!this.check) {
                    $('#search-user-div').fadeIn()
                    this.check = true
                } else {
                    $('#search-user-div').fadeOut()
                    this.check = false
                }
            }
            if (this.cmd === 'addMember') {
                if (!this.check) {
                    $("#add-member-div").fadeIn()
                    this.check = true
                } else {
                    $("#add-member-div").fadeOut()
                    this.check = false
                }
            }
            if (this.cmd === 'changeOwner') {
                if (!this.check) {
                    $('#change-owner-div').fadeIn()
                    this.check = true
                } else {
                    $('#change-owner-div').fadeOut()
                    this.check = false
                }
            }
            if (this.cmd === 'addComment') {
                if (!this.check) {
                    $('#add-comment-div').fadeIn()
                    this.check = true
                } else {
                    $('#add-comment-div').fadeOut()
                    this.check = false
                }
            }
            // not use
            // if (this.cmd === 'createProject') {
            //     if (!this.check) {
            //         let teamId = e.currentTarget.parentElement.parentElement.getAttribute('teamid')
            //         $('#add-project-div').attr('teamid', teamId)
            //         $("#add-project-div").fadeIn()
            //         this.check = true
            //     } else {
            //         $("#add-project-div").fadeOut()
            //         this.check = false
            //     }
            // }
        }
    }
})

export { icon }