const CHAT_URL = "http://localhost:8083";

function addContact(contactName, lastMessage, username){
    let chatContactsTemplate = document.querySelector('#chat-contacts-template').innerHTML;
    let contactsList = document.querySelector('.contacts-list');
    let profilePic = buildProfilePhotoURL(username);
    let chatContacts = eval('`'+chatContactsTemplate+ '`');
    contactsList.innerHTML += chatContacts;
}

async function addContacts(){
    const USERNAME = localStorage.getItem('username');
    let contactsList = document.querySelector('.contacts-list').innerHTML = "";
    let contacts = await getContacts(USERNAME, 0, 10);
    console.log(contacts);
    contacts.forEach((value, key) => {
        addContact(contacts[key].name, "", contacts[key].username);
    });
}

async function getContacts(username, pageNo, pageSize){
    const URL = CHAT_URL + `/get-all-contacts?username=${username}&pageNo=${pageNo}&pageSize=${pageSize}`;
    let response = await fetch(URL, { 
                            method: "GET",
                            headers: {
                                Authorization: localStorage.getItem("auth"),
                            }
                    });
    response = await response.json();
    return response;
}


function addMessage(message, isSent, date, username){
    let chatSentTemplate = document.querySelector('#sent-message').innerHTML;
    let chatReceivedTemplate = document.querySelector('#received-message').innerHTML;
    let chatWindow = document.querySelector('.chat-window');
    // let messageSent = "Hello how are you??";
    // let messageReceived = "Doing Great..";
    let profilePic = buildProfilePhotoURL(username);
    let chat;
    if(isSent)
        chat = eval('`' + chatSentTemplate + '`');
    else
        chat =  eval('`' + chatReceivedTemplate  + '`'); 
    chatWindow.innerHTML += chat; 

    /* Scrolling chat to the bottom */
    chatWindow.scrollTop = chatWindow.scrollHeight;
}

async function addMessages(memberTwo, pageNo, pageSize){
    // MEMBER_ONE is current user's username
    const MEMBER_ONE = localStorage.getItem('username');
    let messages = await getAllSortedMessages(MEMBER_ONE, memberTwo, pageNo, pageSize);
    console.log(messages);
    messages.forEach((value, key) => {
        let date = formatDate(messages[key].date);
        
        if(messages[key].sender === MEMBER_ONE){
            let username = messages[key].sender;
            addMessage(messages[key].message, true, date, username);
        }
        else{
            let username = messages[key].sender;
            addMessage(messages[key].message, false, date, username);
        }
    });
}

async function getAllSortedMessages(memberOne, memberTwo, pageNo, pageSize){
    const URL = CHAT_URL + `/get-all-sorted-messages?memberOne=${memberOne}&memberTwo=${memberTwo}&pageNo=${pageNo}&pageSize=${pageSize}`;
    let response = await fetch(URL, { 
                method: "GET",
                headers: {
                    Authorization: localStorage.getItem("auth"),
                }
            });
    response = await response.json();
    return response;
}



async function sendMessage(){
    console.log("Message Sent-------->>>>>>>>><<<<<<<<<<<");

    let chatInput = document.querySelector('.chat-input-box');
    const CHAT = chatInput.value;
    if(CHAT === "" || CHAT === null)
        return;

    const date = new Date();
    const CURRENT_DATE = new Date(date.getTime() - (date.getTimezoneOffset() * 60000)).toJSON();
    const RECEIVER =  document.querySelector('.chat-window-container').dataset.contactingUsername;
    const SENDER = localStorage.getItem('username');

    data = JSON.stringify({
        date: CURRENT_DATE,
        message: CHAT,
        receiver: RECEIVER,
        sender: SENDER,
        time: CURRENT_DATE
    });

    const URL = CHAT_URL + "/add-message";
    let response = await fetch(URL, {
        method: "POST",
        headers: {
            'Content-Type': 'application/json',
            Authorization: localStorage.getItem("auth"),
        },
        body: data,
    })
    response = await response.text();
    console.log(response);

    if(response === "true"){
        addMessage(CHAT, true, formatDate(CURRENT_DATE), SENDER);
        chatInput.value = "";
    }
    else{
        renderNotification("Some Network Connectivity Issue.!!");
    }


}


/**
 * 
 * @param {Date} date 
 * @returns {String} formattedDate
 */
function formatDate(date){
    let messageDate =  new Date(date);
    messageDate.setHours(messageDate.getHours()-5);
    messageDate.setMinutes(messageDate.getMinutes()-30);
    return messageDate.toDateString() + " ● " + messageDate.toLocaleTimeString();
}



/**
 * Runs when document is loaded on page
 */
$(document).ready(function(){
    addContacts();

    // set chat contacts window profile photo
    const USERNAME = localStorage.getItem('username');
    document.querySelector('.message-contact-profile-photo')
            .setAttribute('src', buildProfilePhotoURL(USERNAME));
    
    document.querySelector('.chat-send-btn').addEventListener('click',sendMessage);

});


/**
 * 
 * @param {String} username 
 * @returns {String} url for profile picture
 */
function buildProfilePhotoURL(username){
    return  USER_PROFILE_URL +
            "/user/get-profile-img-by-username?username=" +
            username +
            "&ifMini=true";
}

/**
 * Responsible fro opening chats: Opens when chat button is clicked.
 */
function message(){
    if (flag==0){
        document.querySelector(".message").classList.toggle("hide");
    }
    else{
        addContacts();
        document.querySelector(".message").classList.toggle("hide");
        document.querySelector(".chat").classList.toggle("hide");
        flag=0; 
    } 
}


var flag=0; //global flag for chat window use


/**
 * Runs when chat window opens
 */
function chatwindow(element){
    flag=1;
    $('.chat').removeClass('hide');
    var name = element.querySelector('.contact-name').textContent;

    console.log(element);
    document.querySelector('.chat-window').innerHTML = "";
    let contactedUser = element.dataset.contactUsername;
    addMessages(contactedUser, 0, 50);           // TODO: pageSize & pageNo to change dynamically.

    document.querySelector('.current-contact-profile-photo')
            .setAttribute('src', buildProfilePhotoURL(contactedUser));
    $('.chat-header').html(name);
    console.log(name);

    /*
     adding the username to chat window container to
     get the receiver of the message.
    */
    document.querySelector('.chat-window-container').dataset.contactingUsername = contactedUser;
}

function chatwindowclose(){
    flag=0;
    $('.chat').addClass('hide');
}  
