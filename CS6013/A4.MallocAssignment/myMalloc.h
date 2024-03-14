/**
 * Assignment 4: Malloc Replacement Assignment
 * Purpose: This assignment we are allocating/deallocating virtual memory to implement the library functions malloc() and free()
 *
 * Author: Samantha Pope
 * Date: March 11, 2024
 *
 * myMalloc Class: contains the allocate and deallocate memory. so this object is what actually replaces the malloc() syscall
 * contains a hash table to keep track of allocated addresses and their sizes
 *
 */

#ifndef A4_MALLOCASSIGNMENT_MYMALLOC_H
#define A4_MALLOCASSIGNMENT_MYMALLOC_H
#include "hashTables.h" // Include the hash table header
#include <cstddef> // for size_t
#include <sys/mman.h>
#include <unistd.h>
#include <cstdint>    // For size_t
#include <cassert>    // For assert

class MyMalloc {

    public:
        MyMalloc();
        ~MyMalloc();
        void* allocate(size_t bytesToAllocate);
        void deallocate(void* ptr);

    private:
        LinearProbingHashTable hashTable; // Utilize your hash table implementation

};

#endif //A4_MALLOCASSIGNMENT_MYMALLOC_H
