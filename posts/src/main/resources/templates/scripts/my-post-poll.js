/* These global variable already defined in CollreachPosts.js*/

// const POSTS_URL = "http://localhost:8084";
// const USER_PROFILE_URL = "http://localhost:8082";
// let firstLoad = true;
// let globalPageNumber = 0;


/**
 * 
 * @param {boolean} post
 * Signifies if to load posts or poll
 * If set to true it loads posts else polls  
 * @returns 
 */
async function load(post) {
    login();

    if (firstLoad) {
        getUserDetails();
        firstLoad = !firstLoad;
    }

    if (navigator.cookieEnabled === false) {
        alert("We use cookies to make this website functional. Please enable cookies in your browser.");
        return;
    }


    //const visibility = "CSE";    //getDeptFromCookie();  -> to be used later   // should come from already logged in user.
    const pageNo = globalPageNumber;                                   // should change dynamically
    const pageSize = 5;                                // fixed value -> for how many pages to be loaded in the DOM in each request.

    const template = document.getElementById('loading-anim-template').innerHTML;
    //displayLoading(template,'posts-loading-anim');

    let username = localStorage.getItem('username');
    let url = POSTS_URL;
    if (post)
        url += "/posts/get-posts/" + username;
    else
        url += "/polls/get-polls/" + username;

    url = buildNewURL(url, pageNo, pageSize);


    let response = await fetch(url, {                                    // Getting all the posts
        method: "GET",
        headers: {
            Authorization: localStorage.getItem("auth"),
        }
    })

    response = await response.json();
    hideLoading("posts-loading-anim");
    response = response.postsSet;
    console.log(response);

    if (response.length === 0) {
        console.log("No more posts......");
        return;
    }


    // preprocessing some data & rendering templates
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

        // preprocessing dates
        response[key].beautifiedDate = beautifyDate(response[key].createDate, response[key].uploadTime);
        response[key].beautifiedTime = beautifyTime(response[key].createDate, response[key].uploadTime);

        //counting days left
        let daysLeft = getDaysLeft(response[key].createDate,
            response[key].uploadTime,
            response[key].lifetimeInWeeks);
        if (response[key].messageId === 0)                // i.e. it's a poll
            response[key].daysLeft = daysLeft === 0 ? "Poll closed" : daysLeft + "d left";
        else                                            // it is a post message
            response[key].daysLeft = daysLeft;

        response[key].profilePhoto = USER_PROFILE_URL +
            "/user/get-profile-img-by-username?username=" +
            username +
            "&ifMini=true";

        // checks if a post is with or without image & preprocesses it.
        if (response[key].image !== null)
            response[key].image = response[key].image.replace(/^/, 'data:image/' + response[key].filetype + ';base64,');

        renderPostAndPollTemplate(response[key]);         // renders conditionally according to posts or poll.
    });

    let postsContainer;
    if (post)
        postsContainer = document.querySelector('.my-posts');
    else
        postsContainer = document.querySelector('.my-polls');
    let nthElemNo = postsContainer.childElementCount - 4 + 1;
    let nthElem = postsContainer.children[nthElemNo - 1];
    if (postsContainer.childElementCount > 5)
        lazyLoadPosts(nthElem);
}


function renderPostAndPollTemplate(data) {
    const postWithImgTemplate = document.getElementById('post-with-image-template').innerHTML;
    const postWithoutImgTemplate = document.getElementById('post-without-image-template').innerHTML;
    const pollQuestionTemplate = document.getElementById('poll-question-template').innerHTML;

    let template = null;                                      // rendering posts with and without images.
    if (data.messageId !== 0 && data.daysLeft !== 0) {
        if (data.image == null) {
            template = eval('`' + postWithoutImgTemplate + '`');
        }
        else {
            template = eval('`' + postWithImgTemplate + '`');
        }
    }
    else if (data.messageId === 0) {                                           // rendering polls
        let optionsDiv = ``;
        data.answers.forEach((value, key) => {
            data.answers[key].answer = eliminateTagsFromString(data.answers[key].answer);
            optionsDiv += `<div>
                                <button value= ${data.answers[key].answer}
                                        data-p-id=${data.messageId}
                                        data-a-id=${data.answers[key].answerId}
                                        data-percentage=${data.answers[key].percentage}%
                                        class="btn btn-outline-primary polled-option-btn"
                                        style = "background-image: linear-gradient(to left, #fff ${100 - data.answers[key].percentage}%, #6c67fd 15%);">
                                    <span style = "float:left; color: black;">${data.answers[key].answer}</span> 
                                    <span style = "float:right;color:#6c67fd"> ${data.answers[key].percentage}%</span>
                                </button>
                        </div>`;
        });

        data["options"] = optionsDiv;
        template = eval('`' + pollQuestionTemplate + '`');
    }

    let postsContainer;
    if (template !== null) {
        if (data.messageId !== 0)                                  // i.e. if it is a post
            postsContainer = document.querySelector('.my-posts');
        else                                                        // if it is a poll
            postsContainer = document.querySelector('.my-polls');
        postsContainer.innerHTML += template;
    }

    countViews();
}


function buildNewURL(url, pageNo, pageSize) {
    return `${url}?pageNo=${pageNo}&pageSize=${pageSize}`;
}


function postwindow(postbutton) {
    $(".my-posts").empty();
    globalPageNumber = 0;
    load(true);
    $(".my-posts").css('display', 'block');
    $(".my-polls").css('display', 'none');
    $(".post-toggle-btn").css('background-color', '#6c67fd');
    $(".post-toggle-btn").css('color', 'white');
    $(".poll-toggle-btn").css('color', '#6c67fd');
    $(".poll-toggle-btn").css('background-color', 'white');
    $(".del-btn-box").addClass("none");
}


function pollwindow(pollbutton) {
    $(".my-polls").empty();
    globalPageNumber = 0;
    load(false);
    $(".my-posts").css('display', 'none');
    $(".my-polls").css('display', 'block');
    $(".poll-toggle-btn").css('background-color', '#6c67fd');
    $(".poll-toggle-btn").css('color', 'white');
    $(".post-toggle-btn").css('color', '#6c67fd');
    $(".post-toggle-btn").css('background-color', 'white');
    $(".del-btn-box").addClass("none");
}


function showdelbtn(delbox) {
    delbox.nextElementSibling.classList.toggle("none");
}