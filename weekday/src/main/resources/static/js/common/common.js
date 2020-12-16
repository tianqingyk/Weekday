function getQueryString(name) {
    let reg = new RegExp('(^|&)' + name + '=([^&]*)(&|$)', 'i');
    let r = window.location.search.substr(1).match(reg);
    if (r != null) {
        return unescape(r[2]);
    }
    return null;
}

function resetPassword() {
    let success = getQueryString('success')
    if (success === 'true') {
        alert('Reset Password: An email has been send to you email!')
    }
    if (success === 'resetSuccess') {
        alert('Your password has been reset!')
    }
}

export { getQueryString, resetPassword }