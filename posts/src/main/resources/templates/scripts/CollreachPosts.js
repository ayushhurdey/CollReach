const POSTS_URL = "http://localhost:8084";
const USER_PROFILE_URL = "http://localhost:8082";
let firstLoad = true;

function load() {
    if (firstLoad) {
        getUserDetails();
        firstLoad = !firstLoad;
    }
    const visibility = "CSE";             // should come from already logged in user.
    const pageNo = 0;                     // should change dynamically
    const pageSize = 10;                  // fixed value -> for how many pages to be loaded in the DOM in each request.

    const template = document.getElementById('loading-anim-template').innerHTML;
    //displayLoading(template,'posts-loading-anim');


    let url = POSTS_URL + "/get-paged-posts";
    url = buildURL(url, pageNo, pageSize, visibility);


    fetch(url, {                                    // Getting all the posts
        method: "GET",
        headers: {
            Authorization: localStorage.getItem("auth"),
        }
    })
        .then((response) => response.text())
        .then((res) => {
            hideLoading("posts-loading-anim");
            let response = JSON.parse(res).postsSet;
            console.log(response);

            // preprocessing some data
            response.forEach((value, key) => {
                let username = response[key].username;
                // counting total votes 
                let totalVotesCount = 0;
                if (response[key].pollId !== 0) {
                    response[key].answers.forEach((value2, key2) => {
                        totalVotesCount += response[key].answers[key2].votes;
                    });
                    response[key]["totalPollVotes"] = totalVotesCount;
                }

                //counting days left
                response[key].daysLeft = getDaysLeft(response[key].createDate,
                    response[key].uploadTime,
                    response[key].lifetimeInWeeks);

                //console.log(response[key].messageId + " -> " + response[key].visibility);
                //console.log(response[key].messageId + " -> " + response[key].message);
                response[key].profilePhoto = USER_PROFILE_URL +
                    "/user/get-profile-img-by-username?username=" +
                    username +
                    "&ifMini=true";
                if (response[key].image !== null)
                    response[key].image = response[key].image.replace(/^/, 'data:image/' + response[key].filetype + ';base64,');

                renderPostTemplate(response[key]);         // renders conditionally according to posts or poll.
            });

        });
}


function renderPostTemplate(data) {
    const postWithImgTemplate = document.getElementById('post-with-image-template').innerHTML;
    const postWithoutImgTemplate = document.getElementById('post-without-image-template').innerHTML;
    const pollQuestionTemplate = document.getElementById('poll-question-template').innerHTML;
    const pollAnswersTemplate = document.getElementById('poll-answers-template').innerHTML;


    let template;                                      // rendering posts with and without images.
    if (data.messageId !== 0) {
        if (data.image == null) {
            template = eval('`' + postWithoutImgTemplate + '`');
        }
        else {
            template = eval('`' + postWithImgTemplate + '`');
        }
    }
    else {                                           // rendering polls
        let optionsDiv = ``;
        data.answers.forEach((value, key) => {
            optionsDiv += `<div>
                                <button
                                    value="${data.answers[key].answer}"
                                    data-p-id="${data.pollId}"
                                    data-a-id="${data.answers[key].answerId}" 
                                    class="btn btn-outline-primary poll-option-btn">
                                    ${data.answers[key].answer}
                                </button>
                            </div>`;
        });
        data["options"] = optionsDiv;
        template = eval('`' + pollQuestionTemplate + '`');
    }

    let postsContainer = document.getElementById('posts-container');
    postsContainer.innerHTML += template;

    countViews();
}


function buildURL(url, pageNo, pageSize, visibility) {
    return `${url}?pageNo=${pageNo}&pageSize=${pageSize}&visibility=${visibility}`;
}


function getDaysLeft(dateCreated, uploadTime, lifetimeInWeeks) {
    let today = new Date();

    let expiryDate = new Date(dateCreated + "T" + uploadTime);
    expiryDate.setDate(expiryDate.getDate() + lifetimeInWeeks * 7);

    var difference_In_Time = expiryDate.getTime() - today.getTime();
    var difference_In_Days = difference_In_Time / (1000 * 3600 * 24);
    difference_In_Days = (difference_In_Days < 0) ? 0 : difference_In_Days;
    return Math.ceil(difference_In_Days);
}


// function getAllUsers(listOfUsernames){
//     url = USER_PROFILE_URL + "/user/get-users-from-username";
//     fetch(url, {
//             method: "POST",
//             headers: {
//                 'Content-Type': 'application/json',
//                 Authorization: localStorage.getItem("auth"),
//         },
//         body: JSON.stringify({
//             "usernames": listOfUsernames
//          })
//     })
//     .then((response) => response.text())
//     .then((res) => {
//         console.log(JSON.parse(res).users);
//         return JSON.parse(res).users;
//     });
// }

function getUserDetails() {
    const userDetailsTemplate = document.querySelector('#user-profile-template').innerHTML;
    let username = localStorage.getItem('username');

    const url = USER_PROFILE_URL + "/user/get-user-details/" + username;
    fetch(url, {
        method: "POST",
        headers: {
            'Content-Type': 'application/json',
            Authorization: localStorage.getItem("auth"),
        }
    })
        .then((response) => response.text())
        .then((res) => {
            console.log(JSON.parse(res));
            let data = JSON.parse(res);
            localStorage.setItem('userDetails', res);
            let profileAccessKey = data.userPersonalInfoResponse.profileAccessKey;

            data.userPersonalInfoResponse.profileAccessKey = USER_PROFILE_URL + "/profile/" + profileAccessKey;

            getImageURL = USER_PROFILE_URL +
                "/user/get-image?filename=" +
                data.userPersonalInfoResponse.userProfilePhoto;
            setProfileImageSrc(getImageURL);                                // sets all the profile images on the page
            setUserName(data.userPersonalInfoResponse.name);
            let template = eval('`' + userDetailsTemplate + '`');
            document.querySelector('#announcement-pic').src = getImageURL;

            let userDetailsContainer = document.querySelector('#user-profile-details-container');
            userDetailsContainer.innerHTML += template;
        });
}


function setProfileImageSrc(url) {
    let listOfProfileImgNodes = document.querySelectorAll('img[data-profile-img]');
    listOfProfileImgNodes.forEach(element => {
        element.src = url;
    });
}

function setUserName(name) {
    let listOfProfileNameElems = document.querySelectorAll('span[data-profile-name]');
    listOfProfileNameElems.forEach(element => {
        element.innerText += name;
    });
}

function displayLoading(template, destinationId) {
    const loader = document.getElementById('posts-loading-anim');
    const dest = document.getElementById(destinationId);
    dest.style.display = "block";
    //loader.classList.add("display");
    //loader.style.display = "block";
    loader.innerHTML = eval('`' + template + '`');
    // to stop loading after some time
    setTimeout(() => {
        //loader.classList.remove("display");
        dest.style.display = "none";
    }, 6000);
}


// hiding loading 
function hideLoading(loaderId) {
    //loader.classList.remove("display");
    const loader = document.getElementById(loaderId);
    loader.style.display = "none";
}


function like(element) {
    let username = localStorage.getItem('username');
    let messageId = element.dataset.mId;
    if (username == null || messageId == null)
        return;

    if (element.dataset.liked.localeCompare("false") === 0) {

        let url = POSTS_URL + "/update-post-likes/" + username + "/" + messageId;
        fetch(url, {
            method: "PUT",
            headers: {
                'Content-Type': 'application/json',
                Authorization: localStorage.getItem("auth"),
            }
        })
            .then((response) => response.text())
            .then((res) => {
                console.log(res);
                if (res.toLowerCase().includes("success")) {
                    element.classList.toggle("fa-star");
                    element.classList.toggle("fa-star-o");
                    element.dataset.liked = "true";
                }
            });
    }
}

function updateLikes() {
    // ------------  incomplete ---------------//
    likeNodes = document.querySelectorAll('.likes');
    likeNodes.forEach(element => {
        element.addEventListener('');
    });
}


function getAllLikes(element) {
    let messageId = element.dataset.messageId;
    let url = POSTS_URL + "/get-all-likes/posts/" + messageId;

    fetch(url, {
        method: "GET",
        headers: {
            'Content-Type': 'application/json',
            Authorization: localStorage.getItem("auth"),
        }
    })
        .then((response) => response.text())
        .then((totalLikes) => {
            console.log(res);
            element.innerText = totalLikes;
        });
}

function postOverlay() {
    var x = document.getElementById('post-overlay');
    var s = document.getElementById('feeds-section');
    $('#a-btn').prop('disabled', true);
    if (x.style.display == 'none') {
        x.style.display = 'block';
        s.style.opacity = '0.4';
    } else {
        x.style.display = 'none';
    }
}


function pollOverlay() {
    var x = document.getElementById('post-overlay');
    var s = document.getElementById('feeds-section');
    var y = document.getElementById('poll-overlay');
    $('.form-control').val('');
    x.style.display = 'none'
    if (y.style.display == 'none') {
        y.style.display = 'block';
        s.style.opacity = '0.4';
    } else {
        y.style.display = 'none';
    }
}

function back() {

    $("#poll-overlay").css('display', 'none');
    $("#post-overlay").css('display', 'block');
}


function exit() {
    let consent = confirm("Your work will be lost. Do you still want to go back?");
    if (consent) {
        $("#poll-overlay").css('display', 'none');
        $("#post-overlay").css('display', 'none');
        $("#feeds-section").css('opacity', '1');
        $('.form-control').val('');
        $('#upload-img').val('');
        $("#preview-box").css('display', 'none');
        $('#upload-img-preview').attr('src', '#');
        $('#a-btn').prop('disabled', false);
    }
}


// $(".custom-file-input").on("change", function () {
//     var fileName = $(this).val().split("\\").pop();
//     $(this).siblings(".custom-file-label").addClass("selected").html(fileName);
// });


function readURL(input) {

    if (input.files && input.files[0]) {
        var reader = new FileReader();

        reader.onload = function (e) {
            $("#preview-box").css('display', 'block');
            $('#upload-img-preview')
                .attr('src', e.target.result)
                .width('100%');
        };

        reader.readAsDataURL(input.files[0]);
    }
}


function addOption() {
    var c = $('#overlay-poll-options .poll-option').length;
    var v = c + 1;
    console.log(v);
    $('#remove-option-btn').css('display', 'block');

    if (v < 6) {

        //console.log(count);
        var d = document.createElement("div");

        var i = document.createElement("input");

        d.setAttribute('class', 'poll-option');

        i.setAttribute('class', 'form-control');
        i.setAttribute('type', 'text');
        i.setAttribute('name', 'option' + v);
        i.setAttribute('placeholder', 'Option ' + v);
        i.setAttribute('size', '40');
        i.setAttribute('maxlength', '50');
        i.setAttribute('id', 'op' + v);


        d.appendChild(i);
        poll = document.getElementById('overlay-poll-options');
        poll.appendChild(d);
    }
    if (v == 5) {
        $('#add-option-btn').css('display', 'none');
    }
}



function removeOption() {
    $('#add-option-btn').css('display', 'block');
    var c = $('#overlay-poll-options .poll-option').length;
    v = c;
    console.log(v);
    if (v > 2) {
        var select = document.getElementById('overlay-poll-options');
        select.removeChild(select.lastChild);
    }
    if (v == 3) {
        $('#remove-option-btn').css('display', 'none');
    }
}

function renderNotification(message) {
    // to be implemented
}

function countViews() {
    let observerConfig = {
        root: null,
        rootMargin: '0px',
        threshold: 0.75
    };
    var intersectionObserver = new IntersectionObserver(function (entries) {
        // If intersectionRatio is 0, the target is out of view
        // and we do not need to do anything.
        entries.forEach(entry => {
            if (entry.target.attributes["data-seen"].nodeValue.localeCompare("true") === 0 || entry.intersectionRatio <= 0)
                return;

            console.log(entry.target.attributes["data-m-id"]);
            console.log('Counted 1 view..');
            updateViewsCount(entry.target.attributes["data-m-id"].nodeValue);
            intersectionObserver.unobserve(entry.target);
            entry.target.attributes["data-seen"].nodeValue = "true";
        });

    }, observerConfig);

    document.querySelectorAll('.outer-post-box-with-image').forEach((elem) => {
        intersectionObserver.observe(elem);
    });

    document.querySelectorAll('.outer-post-box-without-image').forEach((elem) => {
        intersectionObserver.observe(elem);
    });
}


function updateViewsCount(messageId) {
    const USERNAME = localStorage.getItem('username');
    const url = POSTS_URL + "/update-post-views/" + USERNAME + "/" + messageId;
    fetch(url, {
        method: "PUT",
        headers: {
            'Content-Type': 'application/json',
            Authorization: localStorage.getItem("auth"),
        }
    })
        .then((response) => {
            console.log(response);
            //renderNotification(response);
        })
}


function createPost() {
    const url = POSTS_URL + "/create-post";
    const USERNAME = localStorage.getItem('username');
    let date = new Date();
    const currentDate = new Date(date.getTime() - (date.getTimezoneOffset() * 60000)).toJSON();
    let restriction = document.querySelector('#post-view-restriction').value;
    const validity = Number(document.querySelector('#post-validity').value);
    const frequency = Number(document.querySelector('#post-frequency').value);
    const message = document.querySelector('#post-textarea').value;

    const userDetails = JSON.parse(localStorage.getItem('userDetails'));
    if (restriction.toLowerCase().localeCompare("department") === 0) {
        restriction = userDetails.userPersonalInfoResponse.courseInfoResponse.branch;
    }

    data = JSON.stringify({
        createDate: currentDate,
        lifetimeInWeeks: validity,
        message: message,
        recurrences: frequency,
        uploadTime: currentDate,
        userName: USERNAME,
        visibility: restriction
    });

    fetch(url, {
        method: "POST",
        headers: {
            'Content-Type': 'application/json',
            Authorization: localStorage.getItem("auth"),
        },
        body: data,
    })
        .then((response) => response.text())
        .then((messageId) => {
            let img = localStorage.getItem('compressedPostImage');
            if (img === null || img === "" || img.localeCompare("") === 0)
                return "Posted Successfully";

            const imgPostURL = POSTS_URL + "/upload-post-image";
            img = JSON.parse(img);
            const file = Compress.convertBase64ToFile(img.data, img.ext);
            const imgExt = img.ext.replace("image/", ".");

            var formData = new FormData();
            formData.append("file", file, "imageFile" + imgExt);
            formData.append("username", USERNAME);
            formData.append("messageId", Number(messageId));

            fetch(imgPostURL, {
                method: "POST",
                headers: {
                    Authorization: localStorage.getItem("auth"),
                },
                body: formData,
            }).then((resp) => {
                console.log(resp);
                localStorage.removeItem('compressedPostImage');
                renderNotification(resp);
            })
        })
        .then((res) => {
            console.log(res);
        });
}


function createPoll(element) {
    const URL = POSTS_URL + "/create-poll";
    const USERNAME = localStorage.getItem('username');
    let date = new Date();
    const currentDate = new Date(date.getTime() - (date.getTimezoneOffset() * 60000)).toJSON();
    let restriction = document.querySelector('#poll-view-restriction').value;
    const validity = Number(document.querySelector('#poll-validity').value);

    let pollOptionsElementList = document.querySelectorAll('#overlay-poll-options .poll-option');
    let pollQuestion = document.querySelector('#poll-question').value;
    let optionsList = [];

    pollOptionsElementList.forEach(elem => {
        let inputElem = elem.children[0];
        optionsList.push(inputElem.value);
    });

    const userDetails = JSON.parse(localStorage.getItem('userDetails'));
    if (restriction.toLowerCase().localeCompare("department") === 0) {
        restriction = userDetails.userPersonalInfoResponse.courseInfoResponse.branch;
    }

    data = JSON.stringify({
        dateCreated: currentDate,
        validityInWeeks: validity,
        timeCreated: currentDate,
        userName: USERNAME,
        recurrences: 1,
        visibility: restriction,
        answers: optionsList,
        question: pollQuestion
    });

    fetch(URL, {
        method: "POST",
        headers: {
            'Content-Type': 'application/json',
            Authorization: localStorage.getItem("auth"),
        },
        body: data,
    })
        .then((response) => response.text())
        .then((resp) => {
            console.log(resp);
            renderNotification(resp);
        })
}


function polled(element) {

}

/*
var observer = new MutationObserver(function (mutations) {
    mutations.forEach(function (mutation) {
        console.log(mutation)
        if (mutation.addedNodes && mutation.addedNodes.length > 0) {
            // element added to DOM
            var hasClass = [].some.call(mutation.addedNodes, function (el) {
                return el.classList.contains('likes');
            });
            if (hasClass) {
                // element has class `MyClass`
                console.log('element ".likes" added');
            }
        }
    });
});


function addObserverIfDesiredNodeAvailable() {
    var composeBox = document.querySelectorAll(".outer-post-box-with-image")[0];
    if (!composeBox) {
        window.setTimeout(addObserverIfDesiredNodeAvailable, 500);
        return;
    }
    var config = {
        attributes: true,
        childList: true,
        characterData: true,
        subtree: true
    };
    observer.observe(composeBox, config);
}
addObserverIfDesiredNodeAvailable();
*/