
function addContact(contactName, lastMessage){
    let chatContactsTemplate = document.querySelector('#chat-contacts-template').innerHTML;
    let contactsList = document.querySelector('.contacts-list');
    let chatContacts = eval('`'+chatContactsTemplate+ '`');
    contactsList.innerHTML += chatContacts;
}

function addContacts(){
    addContact("Ayush","Just doing it..");
    addContact("Ankit","Ok..");
    addContact("Aradhya","Sure.");
}


function addMessages(){
    let chatSentTemplate = document.querySelector('#sent-message').innerHTML;
    let chatReceivedTemplate = document.querySelector('#received-message').innerHTML;
    let chatWindow = document.querySelector('.chat-window');
    let messageSent = "Hello how are you??";
    let messageReceived = "Doing Great..";

    let chats = eval('`' + chatSentTemplate + chatReceivedTemplate + '`');
    chatWindow.innerHTML += chats; 
}


$(document).ready(function(){
    addContacts();
    addMessages();
});