const CHAT_URL = "http://localhost:8083";
const USER_PROFILE_URL = "http://localhost:8082";
let firstLoad = true;
let globalPageNumber = 0;

function login() {
    const USERNAME = localStorage.getItem('username');
    const AUTH = localStorage.getItem('auth');
    if (USERNAME === null || AUTH === null)
        window.location.replace(USER_PROFILE_URL + "/login");
}

function getDeptFromCookie() {
    let dept = "";
    document.cookie.split(";").forEach(e => {
        if (e.includes("dept=")) {
            dept = e.substr(6);
        }
    })
    if (dept === "")
        login();
    else
        return dept;
}