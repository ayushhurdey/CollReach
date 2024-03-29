/**
 * {URL_ORIGIN, POSTS_URL, USER_PROFILE_URL, CHAT_URL} from './url.js'
 * POSTS_URL = URL_ORIGIN + ":8084";
 * USER_PROFILE_URL = URL_ORIGIN + ":8082";
 * CHAT_URL = URL_ORIGIN + ":8083";
*/

'use strict';

var stompClient = null;

var chatStompClient = null;

function renderNotification(message) {
    let notifier = document.querySelector('.notifier');
    notifier.children[1].innerText = message;
    notifier.removeAttribute('hidden');
    setTimeout(() => {
        notifier.setAttribute('hidden', "");
    }, 5000);
}


function connect() {
    //const URL_ORIGIN = location.origin.replace(/.{0,5}$/, '');
    // var socket = new SockJS(URL_ORIGIN + ':8084/ws');
    // var chatSocket = new SockJS(URL_ORIGIN + ':8083/ws');
    var socket = new SockJS(POSTS_URL + '/ws');
    var chatSocket = new SockJS(CHAT_URL + '/ws');

    stompClient = Stomp.over(socket);
    chatStompClient = Stomp.over(chatSocket);

    stompClient.connect({}, onConnected, onError);
    chatStompClient.connect({}, onConnectedToChat, onError);
}


function onConnected() {
    stompClient.subscribe('/topic/public', onNewPostAdded);
    stompClient.subscribe('/topic/likesUpdate', onMessageReceived);
    stompClient.subscribe('/topic/viewsUpdate', onMessageReceived);
    stompClient.subscribe('/topic/votesUpdate', onVotesUpdate);
}

function onConnectedToChat() {
    const USERNAME = localStorage.getItem('username');
    chatStompClient.subscribe('/topic/' + USERNAME, onNewMessage);
}


function onError(error) {
    renderNotification('!! Some connection issues.')
}

function onNewMessage(payload) {
    let message = JSON.parse(payload.body);
    let chatWindow = document.querySelector('.chat-window-container');
    let currentReceiver = chatWindow.dataset.contactingUsername;
    if (currentReceiver === message.sender) {
        addMessage(message.message, false, formatDate(message.date), message.sender);
        console.log("Message added to window.")
    }
    console.log("current window is not receiver..");
    console.log(JSON.parse(payload.body));
}

function onNewPostAdded(payload) {
    renderNotification(payload);
}

function onMessageReceived(payload) {
    let message = JSON.parse(payload.body);
    let messageId = message.messageId;
    let likes = message.likes;
    let views = message.views;
    let likeElement = document.querySelector(`.star span[data-m-id="${messageId}"]`);
    let viewElement = document.querySelector(`.eye span[data-m-id="${messageId}"]`);
    if (likeElement !== null)
        likeElement.innerText = likes;
    if (viewElement !== null)
        viewElement.innerText = views;
}

function onVotesUpdate(payload) {
    let message = JSON.parse(payload.body);
    let messageId = message.messageId;
    let votes = message.votes;
    let votesElement = document.querySelector(`.poll-info small[data-p-id="${messageId}"]`);
    if (votesElement !== null)
        votesElement.innerText = (votes + " votes");

}

connect();