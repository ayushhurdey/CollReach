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


async function fetchSkills() {
    const URL_ORIGIN = location.origin.replace(/.{0,5}$/, '');
    const SKILLS_LIST = document.querySelector('.skills-list');
    let ALL_SKILLS = [];

    const URL = `${URL_ORIGIN}:8082/user/get-all-skills`;
    let response = await fetch(URL, {
        method: "GET",
        headers: {
            'Content-Type': 'application/json',
            Authorization: localStorage.getItem("auth"),
        }
    });
    let skills = await response.json();
    console.log(response);

    for(skillKey in skills){
        ALL_SKILLS.push(skillKey.toLowerCase());
        var option = document.createElement("OPTION");
        option.setAttribute("value", skillKey.toLowerCase());
        option.setAttribute("data-skill-id", skills[skillKey]);
        var text = document.createTextNode(skillKey.toString());
        option.appendChild(text);
        SKILLS_LIST.appendChild(option);
    };
    console.log(ALL_SKILLS);   
}

$(document).ready(function () {
    fetchUserDetails();
    fetchSkills();
});