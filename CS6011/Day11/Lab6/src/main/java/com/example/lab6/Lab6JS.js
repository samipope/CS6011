
"use strict";

function mainFunct(){

    //display hello world in two ways
    document.writeln("Hello World");
    console.log("Hello world");
    //the difference between the two is that one shows up in the console which would be a good place ot put notes in for other engineers.
    //the other one would be good to use if trying to display to a user.

    //Create an array literal variable (write stuff inside of []) containing a string, a boolean, an int, a floating-point number, and an object.
    let anArray = [3,3.4,'hello',true];
    for(let elem of anArray){
        console.log("Element: ", elem);
    }
    //modify the array somehow and print it again
    anArray[0]=0;
    for(let elem of anArray){
        console.log("Element: ", elem);
    }
//I used safari and didn't notice anything strange. it changed the first thing in my array to a 0 and reprinted it into the console


    //define a function that adds two parameters together
    //C++ syntax stlye
    function add(a,b){
        let c;
        c=a+b;
    }
    //java style
    let myAdd = function(a,b){return a+b;}

    //i prefer the C++ syntax because i am more used to it. i think the java syntax does look more condensed
    //i also like that C++ return is very clearly outlined


    //test the function with ints and strings
    let x = myAdd(3,4);
    document.writeln("this should be 7" + x);
    let y = myAdd(5,9);
    document.writeln("this should be 14" + y);

    let z = myAdd("this","that");
    document.writeln(z);











}

window.onload = mainFunct();