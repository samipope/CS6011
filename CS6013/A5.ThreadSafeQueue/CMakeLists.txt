cmake_minimum_required(VERSION 3.27)
project(A5_ThreadSafeQueue)

set(CMAKE_CXX_STANDARD 17)

add_executable(A5_ThreadSafeQueue
        SerialQueue.h
        SerialTestQueue.cpp
        ConcurrentQueue.h
        ConcurrentQueueTest.cpp
        ConcurrentQueueTest.cpp)