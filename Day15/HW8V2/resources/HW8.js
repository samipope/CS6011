"use strict";

const username = document.getElementById("Username");
const chatroom = document.getElementById("chatroom");
const message1 = document.getElementById("message");


// Create a button and add an event listener to send messages
const sendButton = document.getElementById("sendButton");
sendButton.addEventListener("click", sendMessage);

// WebSocket initialization
let ws = new WebSocket("ws://localhost:8080");
let wsOpen = false;
let userJoined = false;

ws.onopen = function () {
    wsOpen = true;
};

function handleKeyPress(event) {
    if (event.key === "Enter") {
        // get value of chatroom
        if (event.target === chatroom) {
            const chatroomValue = chatroom.value;
            // check that chatroom is within range, if not give error
            if ('a' >= chatroomValue && chatroomValue <= 'z') {
                alert("Chatroom should be all lowercase!");
                chatroom.value = "Enter a valid chatroom ID";
                chatroom.select();
            }
        }
    }
}

function sendMessage() {
    const chatroomValue = chatroom.value;
    const messageValue = message1.value;
    const usernameValue = username.value;


    if ('a' >= chatroomValue && chatroomValue <= 'z') {
        alert("Chatroom should be all lowercase!");
        chatroom.value = "Enter a valid chatroom ID";
        chatroom.select();
        return;
    }

    if (wsOpen) {
        if(!userJoined) {
            // lets a person join the chat room
            ws.send('join ' + usernameValue + ' ' + chatroomValue);
            userJoined=true;
        }
        //lets a message get sent
        ws.send('message ' + messageValue);
    }
    if(!wsOpen){
        //allows a person to leave the chatroom
        ws.send('leave' + usernameValue);
        //TODO print: user has left the chat
    }
}


ws.onmessage = function (event) {
    // message1.value = event.data;
    let msg = event.data;
    let msgObj = JSON.parse(msg);

    if (msgObj.type === 'join') {
        let peoplePar = document.getElementById("People");
        let pplElement = document.createElement("x");
        pplElement.textContent = msgObj.user + " has joined the chat\n";
        peoplePar.appendChild(pplElement);
    } else if (msgObj.type === 'message') {
        let messagePar = document.getElementById("Messages");
        let msgElement = document.createElement("p");
        msgElement.textContent = msgObj.user + ": " + msgObj.message;
        messagePar.appendChild(msgElement);
    }
    else if (msgObj.type ==='leave'){
        let peoplePar = document.getElementById("People");
        let pplLeftElement = document.createElement("l");
        pplLeftElement.textContent = msgObj.user + "has left the chat\n";
        peoplePar.appendChild(pplLeftElement);
    }
};


function handleError() {
    message1.value = "Problem connecting to the server";
}

// Add event listeners for key press on input fields
username.addEventListener("keypress", handleKeyPress);
chatroom.addEventListener("keypress", handleKeyPress);
message1.addEventListener("keypress", (event) => {
    if (event.key === "Enter") {
        sendMessage();
    }
});

