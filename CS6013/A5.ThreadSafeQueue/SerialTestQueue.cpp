////////////////////////////////////////////////////////////////////////
//
// Author: Samantha Pope
// Date: 03.29.2024
//
// CS 6013
//
// This class is where my main is stored. It is where I test my SerialQueue methods
//
// How to compile: c++ -std=c++17 SerialTestQueue.cpp -o SerialTestQueue
// How to use: ./SerialTestQueue
//
////////////////////////////////////////////////////////////////////////
#include "SerialQueue.h"
#include <iostream>
#include <cstdlib> // For rand() and srand()
#include <ctime> // For time()

template <typename T>
void dynamicTest(SerialQueue<T>& queue, int iterations) {
    std::srand(std::time(0)); // seed for random number generation

    for (int i = 0; i < iterations; ++i) {
        T val = static_cast<T>(std::rand() % 100); // generate a random value
        queue.enqueue(val);
        //uncomment to look and check that all threads did get enqueued
     //   std::cout << "Enqueued: " << val << std::endl;
    }

    T dequeuedValue;
    while (queue.dequeue(&dequeuedValue)) {
        //uncomment to look and check that all threads did get dequeued
       // std::cout << "Dequeued: " << dequeuedValue << std::endl;
    }
}

int main() {
    SerialQueue<int> myQueue;

    // basic test with manual enqueue and dequeue
    std::cout << "basic Tests:" << std::endl;
    myQueue.enqueue(1);
    myQueue.enqueue(2);
    int value;
    while (myQueue.dequeue(&value)) {
        std::cout << "dequeued: " << value << std::endl;
    }

    // dynamic test with random elements
    std::cout << "\ndynamic Test with 100 elements:" << std::endl;
    dynamicTest(myQueue, 100);

    std::cout << "reached end of main, tests passed";

    return 0;
}
