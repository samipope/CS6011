 "use strict"

// script.js
const table = document.getElementById("multiplication-table");
const toggleBackground = document.getElementById("toggle-background");

// Function to create the multiplication table
function createMultiplicationTable() {
    const tbody = table.querySelector("tbody");
    for (let i = 1; i <= 10; i++) {
        const row = document.createElement("tr");
        for (let j = 1; j <= 10; j++) {
            const cell = document.createElement(i === 1 || j === 1 ? "th" : "td");
            cell.textContent = i === 1 ? j : j === 1 ? i : i * j;
            row.appendChild(cell);
        }
        tbody.appendChild(row);
    }
}

// Event listener for highlighting on mouseenter and mouseleave
table.addEventListener("mouseover", (event) => {
    if (event.target.tagName === "TD") {
        event.target.classList.add("highlighted");
    }
});

table.addEventListener("mouseout", (event) => {
    if (event.target.tagName === "TD") {
        event.target.classList.remove("highlighted");
    }
});

// Event listener for highlighting on click
let clickedCell = null;

table.addEventListener("click", (event) => {
    if (event.target.tagName === "TD") {
        if (clickedCell) {
            clickedCell.classList.remove("clicked");
        }
        event.target.classList.add("clicked");
        clickedCell = event.target;
    }
});

// Toggle background color animation
let animationEnabled = true;
toggleBackground.addEventListener("click", () => {
    animationEnabled = !animationEnabled;
    document.body.classList.toggle("background-animation", animationEnabled);
});

createMultiplicationTable();
