new Vue({
    el: '#login-pwd',
    template: '<Password />',
    components: {
        'Password': httpVueLoader('/static/js/component/Login.vue')
    }
})
new Vue({
    el: '#edit-pwd',
    template: '<Password />',
    components: {
        'Password': httpVueLoader('/static/js/component/Login.vue')
    }
})
