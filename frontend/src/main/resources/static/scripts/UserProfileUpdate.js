async function fetchUserDetails() {
    const URL_ORIGIN = location.origin.replace(/.{0,5}$/, '');
    const PROFILE_NAME = document.querySelector('.profile-name');
    const PROFILE_IMAGE = document.querySelector('.profile-image');
    const NAV_PROFILE_IMAGE = document.querySelector('.user-profile-photo-icon');
    const USERNAME = localStorage.getItem('username');
    PROFILE_IMAGE.src = `${URL_ORIGIN}:8082/user/get-profile-img-by-username?username=${USERNAME}&ifMini=false`;
    NAV_PROFILE_IMAGE.src = `${URL_ORIGIN}:8082/user/get-profile-img-by-username?username=${USERNAME}&ifMini=false`;

    const URL = `${URL_ORIGIN}:8082/user/get-user-details/${USERNAME} `;
    let response = await fetch(URL, {
        method: "POST",
        headers: {
            'Content-Type': 'application/json',
            Authorization: localStorage.getItem("auth"),
        }
    });
    response = await response.json();
    console.log(response);
    PROFILE_NAME.innerHTML = response.userPersonalInfoResponse.name;
}


$(document).ready(function () {
    fetchUserDetails();
});