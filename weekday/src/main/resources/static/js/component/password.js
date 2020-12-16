let password = Vue.component('password', {
    template: `<div class="input-group">
                <input id=\'password\' class="form-control" name="password" type="password" placeholder="Password">
                <div v-on:click="change($event)" class="input-group-append">
                <span class="input-group-text" v-bind:class="{hide, show}"></span></div></div>`,
    data: function () {
        return {
            hide: true,
            show: false
        }
    },
    methods: {
        change: function (e) {
            let type = e.currentTarget.previousElementSibling.type
            if (type === 'text') {
                e.currentTarget.previousElementSibling.type = 'password'
                this.hide = true
                this.show = false
            } else {
                e.currentTarget.previousElementSibling.type = 'text'
                this.hide = false
                this.show = true
            }
        }
    }
})

let confirm = Vue.component('confirm', {
    template: `<div class="input-group">
                <input type="password" id='cPassword' class="form-control" placeholder="Confirm Your Password"
                       @input="validate">
                <span id="tishi"></span>
              </div>`,
    methods: {
        validate: function () {
            let inputPassword = document.getElementById("password").value;
            let confirmPassword = document.getElementById("cPassword").value;
            if (inputPassword === confirmPassword) {
                document.getElementById("tishi").innerHTML = "<p style='color:green'>Approved</p>";
                document.getElementById("submit").disabled = false;
            } else {
                document.getElementById("tishi").innerHTML = "<p style='color:red'>Not Approved</p>";
                document.getElementById("submit").disabled = true;
            }
        }
    }
})

export { password, confirm }