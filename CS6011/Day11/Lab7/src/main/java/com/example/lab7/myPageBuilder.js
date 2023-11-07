"use strict";

function mainFunct() {
    let myHeader = document.createElement("h1"); // Change 'head' to 'h1'
    let headerTxt = document.createTextNode("Welcome to Learning about ANTS!");
    myHeader.appendChild(headerTxt);
    document.body.appendChild(myHeader);
    myHeader.style.backgroundColor = "purple";
    myHeader.style.fontSize = "24px";
    myHeader.style.fontWeight = "bold";

    let myPar = document.createElement("p");
    let myText = document.createTextNode("Ants are very social insects, and they divide jobs among different types of ants in each colony. The queen or queens have only one job - to lay eggs. All other female ants are workers; they feed the larvae, take out the colony's trash, forage for food and supplies, or defend the nest. Male ants' only job is to mate with the queen.");
    myPar.appendChild(myText);
    document.body.appendChild(myPar);
    myPar.style.backgroundColor = "pink";
    myPar.style.fontSize = "14px";

    let newPar = document.createElement("p"); // Change 'x' to 'p'
    let newText = document.createTextNode("Instead of hearing through auditory canals, ants \"hear\" by feeling vibrations in the ground. Special sensors on their feet and on their knees help ants interpret signals from their surroundings. They also use their antennae and the hairs on their body to feel around while foraging for food.");
    newPar.appendChild(newText); // Append newText to newPar
    document.body.appendChild(newPar);
    newPar.style.backgroundColor = "yellow";
    newPar.style.fontSize = "22px";
    newPar.style.font ="comic sans";
    newPar.style.color = "red";

}

window.onload = mainFunct; // Remove the parentheses
