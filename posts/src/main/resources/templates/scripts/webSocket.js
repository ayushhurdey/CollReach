'use strict';

var stompClient = null;

function renderNotification(message) {
    let notifier = document.querySelector('.notifier');
    notifier.children[1].innerText = message;
    notifier.removeAttribute('hidden');
    setTimeout(() => {
        notifier.setAttribute('hidden', "");
    }, 5000);
}


function connect() {
    var socket = new SockJS('http://localhost:8084/ws');
    stompClient = Stomp.over(socket);

    stompClient.connect({}, onConnected, onError);
}


function onConnected() {
    stompClient.subscribe('/topic/public', onNewPostAdded);
    stompClient.subscribe('/topic/likesUpdate', onMessageReceived);
    stompClient.subscribe('/topic/viewsUpdate', onMessageReceived);
    stompClient.subscribe('/topic/votesUpdate', onVotesUpdate);
}


function onError(error) {
    renderNotification('!! Some connection issues.')
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