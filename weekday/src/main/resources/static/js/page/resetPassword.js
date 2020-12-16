import { getQueryString } from "../common/common.js";
import { confirm, password } from "../component/password.js";

$(document).ready(function () {
    let success = getQueryString('success')
    if (success === 'false') {
        alert('This email has not been registered!')
    }
    if (success === 'expired') {
        alert('Reset password link expired, please do it again')
        window.location = "/forgetPassword"
    }

    let encode = getQueryString('encode')
    if (encode !== null) {
        $('#encode').val(encode)
    }
})

new Vue({
    el: '#reset-pwd',
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
