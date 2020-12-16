import { confirm, password } from "../component/password.js";

new Vue({
    el: '#register-pwd',
    components: {
        'Password': password
    }
})

new Vue({
    el: '#confirmPassword',
    components: {
        'Confirm': confirm
    }
})
