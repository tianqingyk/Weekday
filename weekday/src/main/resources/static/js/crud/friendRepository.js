let friend, friendArray

function saveFriend(body) {
    friendArray = []
    body.forEach(element => {
        friendArray.push((element))
    })
    sessionStorage.setItem('friend', JSON.stringify(friendArray))
}

function getFriend() {
    friend = JSON.parse(sessionStorage.getItem('friend'))
    return friend
}

function getFriendById(id) {
    let searchFriend = ''
    friend = getFriend()
    friend.forEach(element => {
        if (element.id === Number(id)) {
            searchFriend = element
        }
    })
    return searchFriend
}

export { getFriend, saveFriend, getFriendById }