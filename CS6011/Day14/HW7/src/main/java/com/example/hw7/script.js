"use strict";

const xValue = document.getElementById("xValue");
const yValue = document.getElementById("yValue");
const resultInput = document.getElementById("result");

//no button - we might create it later on

//we will listen to key press event on xValue and yValue

xValue.addEventListener("keypress", handleKeyPress);
yValue.addEventListener("keypress", handleKeyPress);


//option 2 - websockets
let ws = new WebSocket("ws://localhost:8080");
let wsOpen = false;

ws.onopen = function () {
    wsOpen = true;
}//

ws.onmessage = function (event) {
    resultInput.value = event.data;
}//
function handleKeyPress(event) {
    let y = parseFloat(yValue.value);
    let x = parseFloat(xValue.value);

    // console.log(xValue.value);
    // console.log("Key value: ", event)
    if (event.code == "Enter") {

        if (isNaN(x)) {
            alert("X should be a number!");
            xValue.value = "Enter a number";
            xValue.select();
            return;
        }
        if (isNaN(y)) {
            alert("chatroom name should be all lowercase letters");
            yValue.value = "Enter a valid chatroom name";
            yValue.select();
            return;
        }
        if (wsOpen) {
            //TODO add in some stuff here
            ws.send(x + " " + y);
        } else {
            resultInput.value = "could not open the websocket";
        }


//NAIVE WAY
        //resultInput.value=(x+y);

        //Option 1 - using AJAX
        // let xhr = new XMLHttpRequest();
        // xhr.open("GET", "http://localhost:8080/calculate?x=" +x+y+"&y="+y);
        // xhr.onerror = handleError;
        // xhr.onload = handleAjax;
        // xhr.send();




    }//end of the handle key press
}
console.log("x value: ", xValue);

    function handleError (){
        resultInput.value="problem connecting to the server";
    }
    function handleAjax(){
        resultInput.value = req.responseText;

}



