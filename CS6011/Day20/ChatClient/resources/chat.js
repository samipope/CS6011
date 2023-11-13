"use strict";

const userNameInput = document.getElementById("user-name")
const roomNameInput = document.getElementById("room-name")
const messageInput = document.getElementById("message-input")
const peopleListContent = document.getElementById("people-list-content")
const peopleCounter = document.getElementById("people-counter")
const messageQueue = document.getElementById("message-queue")

let socket = null
let isLoggedIn = false

const peopleListData = new Set()
const messageListData = []

let isWebSocketOpen = false

const updatePeopleCounter = () => {
    const peopleList = Array.from(peopleListData)
    peopleCounter.textContent = `${peopleList.length} Online User${peopleList.length > 1 ? 's' : ''}`
}

const getNamePrompt = (user) => {
    const isUserThemselves = isCurrentUser(user)
    return user + (isUserThemselves ? "(you)" : "")
}

const addUserNameListItem = (user) => {
    const userNameListItem = document.createElement("li")
    userNameListItem.textContent = getNamePrompt(user)
    if (isCurrentUser(user)) {
        userNameListItem.style.color = "green"
        userNameListItem.style.fontWeight = "bold"
    }
    peopleListContent.appendChild(userNameListItem)
}

const updatePeopleList = () => {
    peopleListContent.innerHTML = ""
    const peopleList = Array.from(peopleListData)
    peopleList.forEach(addUserNameListItem)
    updatePeopleCounter()
}


const addNewUser = (user) => {
    peopleListData.add(user)
    updatePeopleList()
}

const removeUser = (user) => {
    peopleListData.delete(user)
    updatePeopleList()
}

const getUserNameInputValue = () => userNameInput.value
const getRoomNameInputValue = () => roomNameInput.value
const getMessageInputValue = () => messageInput.value

const isUserNameInputValueValid = () => {
    const userNameInputValue = getUserNameInputValue()
    const isEmptyString = userNameInputValue.length === 0
    const containsSpaces = !!userNameInputValue.match(/\s/)
    return !isEmptyString && !containsSpaces
}
const isRoomNameInputValueValid = () => {
    const roomNameInputValue = getRoomNameInputValue()
    const isEmptyString = roomNameInputValue.length === 0
    const isLowerCased = roomNameInputValue === roomNameInputValue.toLowerCase()
    const containsSpaces = !!roomNameInputValue.match(/\s/)
    return !isEmptyString && isLowerCased && !containsSpaces
}
const isMessageInputValueValid = () => {
    const messageInputValue = getMessageInputValue()
    return messageInputValue.length > 0
}

const checkIfUserCanJoinRoom = () => {
    if (isLoggedIn) {
        return false
    }
    if (!isUserNameInputValueValid()) {
        alert("Please enter a user name with no spaces!")
        return false
    }
    if (!isRoomNameInputValueValid()) {
        alert("Please enter a lower cased room name with no spaces!")
        return false
    }
    return true
}

const checkIfUserCanSendMessage = () => {
    return isLoggedIn && isMessageInputValueValid()
}

const isCurrentUser = (user) => {
    return user === getUserNameInputValue()
}

const updateDocumentTitle = (user, room) => {
    if (isCurrentUser(user)) {
        document.title = `${getUserNameInputValue()}'s Chat Room: ${room}`
    }
}

const addTimestampDiv = (time, parent) => {
    const timestampDiv = document.createElement("div");
    timestampDiv.style.fontWeight = "normal"
    timestampDiv.style.fontSize = "x-small"
    timestampDiv.style.textAlign = "right"
    timestampDiv.style.marginTop = "4px"
    timestampDiv.textContent = time
    parent.appendChild(timestampDiv)
}

const addJoinMessage = (user, room, newMessage, time) => {
    if (!peopleListData.has(user)) {
        newMessage.textContent = `${user} enters the room`
        newMessage.style.color = "gray"
        newMessage.style.fontWeight = "bold"
        newMessage.style.borderColor = "gray"
        addNewUser(user)
        updateDocumentTitle(user, room)
        addTimestampDiv(time, newMessage)
    }
}

const addLeaveMessage = (user, newMessage, time) => {
    if (peopleListData.has(user)) {
        newMessage.textContent = `${user} leaves the room`
        newMessage.style.color = "gray"
        newMessage.style.fontWeight = "bold"
        newMessage.style.borderColor = "gray"
        removeUser(user)
        addTimestampDiv(time, newMessage)
    }
}

const stripHTMLTags = string => string.replace(/(<([^>]+)>)/gi, "")

const addReceivedMessage = (user, newMessage, message, time) => {
    const isUserThemselves = isCurrentUser(user)
    if (isUserThemselves) {
        newMessage.style.color = "green"
        newMessage.style.border = "4px green solid"
    }
    console.log("before strip", message)
    message = stripHTMLTags(message)
    message = message.replace(/(\[newline\])/g, "<br>")
    newMessage.innerHTML = `<p>${getNamePrompt(user)}: ${message}</p>`
    addTimestampDiv(time, newMessage)
}

const addNewMessage = (type, room, user, message, time) => {
    if (room !== getRoomNameInputValue()) {
        return
    }
    if (messageListData.length === 0) {
        messageQueue.innerHTML = ""
    }
    messageListData.push({type, room, user, message, time})
    const newMessage = document.createElement('div')
    newMessage.style.border = "2px black solid"
    newMessage.style.display = "flex"
    newMessage.style.flexDirection = "column"
    switch (type) {
        case 'join':
            addJoinMessage(user, room, newMessage, time)
            break
        case 'leave':
            addLeaveMessage(user, newMessage, time)
            break
        case 'message':
            addReceivedMessage(user, newMessage, message, time)
            break
    }
    messageQueue.appendChild(newMessage)
}

const handleEnterKeyPress = (event) => {
    if (event.key !== 'Enter') {
        return
    }
    if (isWebSocketOpen) {
        if (checkIfUserCanSendMessage()) {
            const timestamp = new Date().getTime()
            socket.send(`${getUserNameInputValue()} ${getRoomNameInputValue()} ${timestamp} ${getMessageInputValue()}`)
        } else if (checkIfUserCanJoinRoom()) {
            const timestamp = new Date().getTime()
            socket.send(`join ${getUserNameInputValue()} ${getRoomNameInputValue()} ${timestamp}`)
        }
    } else {
        alert("web socket is closed")
    }
}

window.addEventListener("keydown", handleEnterKeyPress)

window.onbeforeunload = function () {
    alert('yes');
};

const handleWindowClose = () => {
    if (isWebSocketOpen) {
        const timestamp = new Date().getTime()
        socket.send(`leave ${getUserNameInputValue()} ${getRoomNameInputValue()} ${timestamp}`)
    } else {
        alert("web socket is close")
    }
}

// disable onclose handler first
// send leave message before the socket closes aka sending the closing frame (OPCODE: 8)
window.onbeforeunload = () => {
    socket.onclose = () => {
    }
    handleWindowClose()
    socket.close()
}

const updateUIAfterLoggingIn = () => {
    isLoggedIn = true
    userNameInput.disabled = true
    roomNameInput.disabled = true
    messageInput.disabled = false
}

const clearMessageAndFocus = () => {
    messageInput.value = null
    messageInput.focus()
}

const displayEmptyMessageListTip = () => {
    const emptyMessageListTip = document.createElement('div')
    emptyMessageListTip.textContent = "No message history."
    emptyMessageListTip.style.color = "gray"
    emptyMessageListTip.style.fontWeight = "bold"
    emptyMessageListTip.style.border = "2px gray dashed"
    messageQueue.appendChild(emptyMessageListTip)
}

const handleMessage = (serverMessage) => {
    const data = JSON.parse(serverMessage.data)
    let {type, room, user, message, timestamp, error} = data
    if(type === "error")
    {
        alert(error)
        return
    }
    const dateObj = new Date(Number(timestamp))
    const time = `${dateObj.toLocaleDateString()} ${dateObj.toLocaleTimeString()}`
    addNewMessage(type, room, user, message, time)
    switch (type) {
        case 'join':
            updateUIAfterLoggingIn()
            break
        case 'message':
            clearMessageAndFocus()
            break
    }
}

const handleError = (error) => {
    alert("socket error, see console for error message");
    console.error(error)
}

const handleClose = () => {
    console.log("web socket is close")
    isWebSocketOpen = false
}

const handleOpen = () => {
    console.log("web socket is open")
    isWebSocketOpen = true
}

const setupWebSocket = () => {
    socket = new WebSocket("ws://localhost:8080")
    socket.onopen = handleOpen
    socket.onclose = handleClose
    socket.onerror = handleError
    socket.onmessage = handleMessage
}

const getRandomString = (length) => {
    length = Math.max(1, length)
    let result = '';
    const characters = 'ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz';
    const charactersLength = characters.length;
    for (let i = 0; i < length; i++) {
        result += characters.charAt(Math.floor(Math.random() * charactersLength));
    }
    return result;
}

const getRandomUserName = () => {
    const string = getRandomString(Math.floor(Math.random() * 10))
    return string.charAt(0).toUpperCase() + string.slice(1).toLowerCase();
}

const getDefaultRoomName = () => "room42"

window.onload = () => {
    setupWebSocket()
    updatePeopleCounter()
    displayEmptyMessageListTip()
    messageInput.disabled = true
    userNameInput.value = getRandomUserName()
    roomNameInput.value = getDefaultRoomName()
}