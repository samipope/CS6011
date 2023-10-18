"use script"



//my "compare" variable that gets passed in allows the user to define how they would like the data sorted
function findMinLocation(arr, start, compare) {
    let minIndex = start;
    for (let i = start + 1; i < arr.length; i++) {
        if (compare(arr[i], arr[minIndex]) < 0) {
            minIndex = i;
        }
    }
    return minIndex;
}

function selectionSort(arr, compare) {
    for (let i = 0; i < arr.length - 1; i++) {
        const minIndex = findMinLocation(arr, i, compare);
        if (minIndex !== i) {
            const temp = arr[i];
            arr[i] = arr[minIndex];
            arr[minIndex] = temp;
        }
    }
}

//ints are working
const intArray = [3, 1, 4, 1, 5, 9, 2, 6, 5, 3];
selectionSort(intArray, (a, b) => a - b);
console.log("Sorted Integers:", intArray);

//floats are working
const floatArray = [3.14, 1.0, 2.71, 0.5, 7.0];
selectionSort(floatArray, (a, b) => a - b);
console.log("Sorted Floats:", floatArray);

//strings are working!
const stringArray = ["apple", "Banana", "cherry", "Date", "Elderberry"];
const stringArray1 = ["apple", "Banana", "cherry", "Date", "Elderberry"];

//my sort function, as is, is case sensitive. it organizes one case and then the other.
//i made another example that shows when i make them all lowercase, it can sort them
selectionSort(stringArray1, (a, b) => a.toLowerCase().localeCompare(b.toLowerCase()));
console.log("Sorted Strings (case-insensitive):", stringArray);
selectionSort(stringArray, (a, b) => b.toLowerCase().localeCompare(a.toLowerCase()));
console.log("Reverse Sorted Strings (case-insensitive):", stringArray);


//if i change the < to > in my compare variable (which is similar to compareTo in C++, then
//it did not work
// const floatArray1 = [3.14, 1.0, 2.71, 0.5, 7.0];
// selectionSort(floatArray1, (b, a) <= a - b);
// console.log("Sorted Floats with switched operator >:", floatArray1);




//sorting people
//making a list of people with first and last names
const people = [
    { first: "John", last: "Doe" },
    { first: "Sami", last: "Pope" },
    { first: "Freddy", last: "Smith" },
]; //have to have the semicolon


function sortByLastNameThenFirstName(a, b) {
    if (a.last === b.last) {
        return a.first.localeCompare(b.first);
    }
    return a.last.localeCompare(b.last);
}

//making a list of people with first and last names
const people1 = [
    { first: "John", last: "Doe" },
    { first: "Sami", last: "Pope" },
    { first: "Freddy", last: "Smith" },
]; //have to have the semicolon

function sortByFirstNameThenLastName(a, b) {
    if (a.first === b.first) {
        return a.last.localeCompare(b.last);
    }
    return a.first.localeCompare(b.first);
}


selectionSort(people, sortByLastNameThenFirstName);
console.log("Sorted People by Last Name, then First Name:", people);

selectionSort(people1, sortByFirstNameThenLastName);
console.log("Sorted People by First Name, then Last Name:", people1);


