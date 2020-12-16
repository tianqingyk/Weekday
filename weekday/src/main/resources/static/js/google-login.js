function onSuccess(googleUser) {
    console.log('Logged in as: ' + googleUser.getBasicProfile().getName());
    sessionStorage.setItem("google", "success");
    let token = googleUser.getAuthResponse().id_token;
    const url = "google?tokenId=" + token
    // console.log(url);
    let httpRequest = new XMLHttpRequest();
    httpRequest.open('GET', url, true);
    httpRequest.send();

    httpRequest.onreadystatechange = function () {
        if (httpRequest.readyState === 4 && httpRequest.status === 200) {
            $(".login-google").attr("id", "success");
            window.location.href = httpRequest.responseURL;
            // socket_send( httpRequest.response);
        }
    };
}

function onFailure(error) {
    console.log(error);
}

function renderButton() {
    gapi.signin2.render('my-signin2', {
        'scope': 'profile email',
        'width': 240,
        'height': 50,
        'longtitle': true,
        'theme': 'dark',
        'onsuccess': onSuccess,
        'onfailure': onFailure
    });
}