////////////////////////////////////////////////////////////////////////
//
// Author: Samantha Pope
// Date: 03.29.2024
//
// CS 6013
//
// This class is where my main is stored to test my concurrentQueue.
//
// How to compile: c++ -std=c++17 ConcurrentQueueTest.cpp -o ConcurrentTestQueue
// How to use: ./ConcurrentTestQueue <numProducers> <NumComsumers> <NumInts>
//
//
////////////////////////////////////////////////////////////////////////
#include "ConcurrentQueue.h"
#include <iostream>
#include <vector>
#include <thread>

/**
 * this makes PRODUCERS: they are threads that ENQUEUE ints
 * @param queue
 * @param num_ints
 */
void producer(ConcurrentQueue<int>& queue, int num_ints) {
    for (int i = 0; i < num_ints; ++i) {
        queue.enqueue(i);
    }
}

/**
 * this makes CONSUMERS: they are threads that DEQUEUE ints
 * @param queue
 * @param num_ints
 */
void consumer(ConcurrentQueue<int>& queue, int num_ints) {
    int dummy;
    for (int i = 0; i < num_ints; ++i) {
        while (!queue.dequeue(&dummy)) {
            // waiting for items to be produced if queue is empty.
            std::this_thread::yield(); // busy waiting
        }
    }
}

bool testQueue(int num_producers, int num_consumers, int num_ints) {
    ConcurrentQueue<int> queue;
    std::vector<std::thread> threads;
    threads.reserve(num_producers + num_consumers);
    // creating producer threads
    for (int i = 0; i < num_producers; ++i) {
        threads.emplace_back(producer, std::ref(queue), num_ints);
    }
    // creating consumer threads
    for (int i = 0; i < num_consumers; ++i) {
        threads.emplace_back(consumer, std::ref(queue), num_ints);
    }
    // wait for all threads to finish
    for (auto& thread : threads) {
        thread.join();
    }
    // check if the final size of the queue is as expected
    int expected_final_size = (num_producers - num_consumers) * num_ints;
    return queue.size() == expected_final_size;
}

int main(int argc, char **argv) {
    if (argc != 4) {
        std::cerr << "How to use: ./ConcurrentQueueTest num_producers num_consumers num_ints" << std::endl;
        return 1;
    }

    int num_producers = std::stoi(argv[1]);
    int num_consumers = std::stoi(argv[2]);
    int num_ints = std::stoi(argv[3]);

    bool result = testQueue(num_producers, num_consumers, num_ints);

    if (result) {
        std::cout << "Test passed: Queue operations are correct." << std::endl;
    } else {
        std::cout << "Test failed: Queue final size mismatch." << std::endl;
    }
    return result ? 0 : 1;
}
