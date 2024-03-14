cmake_minimum_required(VERSION 3.27)
project(A4_MallocAssignment)

set(CMAKE_CXX_STANDARD 17)

add_executable(A4_MallocAssignment
        hashTables.h
        hashTables.cpp
        myMalloc.h
        myMalloc.cpp
        testStruct.cpp)
