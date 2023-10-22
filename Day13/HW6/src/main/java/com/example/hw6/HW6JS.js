"use strict";

//option 1
//let canvas = document.getElementsByTagName("canvas")[0];
//option 2
let canvas = document.getElementById("canvasDrawing");
let context = canvas.getContext("2d");
let cWidth = canvas.width;
let cHeight = canvas.height;
let honeyImg = new Image();
honeyImg.src = "honey2.png";
honeyImg.xPos = 10;
honeyImg.yPos = 10;

let youLost = false;



let beeIMG = new Image();
beeIMG.src = "bee2.png";
//option 1 - let xPos and let yPos (but will make them global variables)
//option 2
beeIMG.xPos = 10;
beeIMG.yPos = 10;

var imgArray = new Array();

imgArray[0] = new Image();
imgArray[0].src = 'bee2.png';
imgArray[0].xPos = 200;
imgArray[0].yPos = 400;


imgArray[1] = new Image();
imgArray[1].src = 'bee2.png';
imgArray[1].xPos = 1000;
imgArray[1].yPos = 40;

imgArray[2] = new Image();
imgArray[2].src = 'bee2.png';
imgArray[2].xPos = 100;
imgArray[2].yPos = 750;

imgArray[3] = new Image();
imgArray[3].src = 'bee2.png';
imgArray[3].xPos = 300;
imgArray[3].yPos = 200;

imgArray[4] = new Image();
imgArray[4].src = 'bee2.png';
imgArray[4].xPos = 600;
imgArray[4].yPos = 750;

imgArray[5] = new Image();
imgArray[5].src = 'bee2.png';
imgArray[5].xPos = 900;
imgArray[5].yPos = 500;

function handleClick(e) {
//update the xPos and yPos of avatar image
    honeyImg.xPos = (e.x-50);
    honeyImg.yPos = (e.y-50);
}

window.onload=function(){
    this.addEventListener('mousemove',mouseMonitor);
}

function animateImg(callbackfn, thisArg){


    eraseOld();


    if (youLost){
        let gameOverIMG = new Image();
        gameOverIMG.src = "gameover.jpg";
        gameOverIMG.xPos=0;
        gameOverIMG.yPos=0;
        //add layer on top of the previous image
        context.drawImage(gameOverIMG,gameOverIMG.xPos,gameOverIMG.yPos,1200,1200);
    }
    else {
        context.drawImage(honeyImg, honeyImg.xPos, honeyImg.yPos, 100, 100);
        for (let i = 0; i < imgArray.length; i++) {

            context.drawImage(imgArray[i], imgArray[i].xPos, imgArray[i].yPos, 100, 100);

            //moving the bee x position
            if (honeyImg.xPos > imgArray[i].xPos) {
                imgArray[i].xPos += 3;
            }
            if (honeyImg.xPos < imgArray[i].xPos) {
                imgArray[i].xPos -= 3;
            }

            //moving the bee y position
            if (honeyImg.yPos > imgArray[i].yPos) {
                imgArray[i].yPos += 3;
            }
            if (honeyImg.yPos < imgArray[i].yPos) {
                imgArray[i].yPos -= 3;
            }


            if ((Math.abs(honeyImg.yPos - imgArray[i].yPos) < 4) && (Math.abs(honeyImg.xPos - imgArray[i].xPos) < 4)) {
                youLost = true;
            }
        }




    }

    window.requestAnimationFrame(animateImg);

}

function mainDrawing(){

        window.requestAnimationFrame(animateImg);


}


function eraseOld(){
    //add layer on top of the previous image
    context.fillStyle="#85c9e3";
    context.fillRect(0, 0, cWidth, cHeight);
}

window.onload=mainDrawing;
document.onmousemove = handleClick;

