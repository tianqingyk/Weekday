function errorHandler(success, errorMessage) {
    if (!success) {
        alert(errorMessage)
    }
}

export { errorHandler }