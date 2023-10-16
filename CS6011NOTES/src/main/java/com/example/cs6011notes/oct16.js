
"use strict";

function mainFunct(){
    //console.log("Testing our JS");
    let myObj = {name: "cs6011", course: "programming"};
    myObj.year = "2023";

    myObj.showDetails = function (){
        console.log("year is: " + myObj.year);
    }
    myObj.showDetails(); //calls the method

    for(let prop in myObj) {
        console.log("Property: ", prop);
        //displays all the properties of the object ("name,
    }
let myArray = [3,3.4,'hello'];
    for(let elem of myArray){
        console.log("Element: ", elem);
    }
    //you can look at this if you go into the webpage, right click and look at inspect -> console


    //use "document" to access all of the document and override it basically
    document.body.style.backgroundColor="lightblue";
    document.writeln("title");


    //manipulating DOM using JS
    let myPar = document.createElement("p");
    let myText = document.createTextNode("This is my paragraph");
    myPar.appendChild(myText);
    document.body.appendChild(myPar);
    myPar.style.backgroundColor="pink";
    myPar.style.fontSize= "20px";
    myPar.style.fontWeight="bold";

    //can use the index of the header in the array to access it
    let h1s = document.getElementsByTagName("h1");
    for(let i in h1s){
         console.log("elements: ", i);}
    let myHeader = h1s[0];
    myHeader.innerHTML= "changing h1 using JS";


    //or can use IDs to access certain headers
    let h3s = document.getElementById("HeaderID3");
    h3s.innerHTML="Changint H3 with JS";
    h3s.appendChild((myText.cloneNode()));








}

window.onload = mainFunct();