import { loginSuccess, setSession } from "../common/commonService.js";

function loginService(success) {
    if (success) {
        setSession()
        loginSuccess()
    }
}

export { loginService }