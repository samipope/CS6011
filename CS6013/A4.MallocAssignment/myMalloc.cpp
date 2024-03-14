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


#include "hashTables.h" // Include the hash table header
#include "myMalloc.h"   // Include the MyMalloc header
#include <sys/mman.h>
#include <unistd.h>
#include <cstdint>    // For size_t
#include <cassert>    // For assert
#include <iostream>

/**
 * constructor --> Creates MyMalloc object
 * Empty because the hashTable takes over the implementation of this
 */
MyMalloc::MyMalloc() {
    // No need to allocate additional memory for the hash table,
    // as it's already done in its constructor.
}

/**
 * destructor
 * Empty because the hashtable destructor takes over
 */
MyMalloc::~MyMalloc() {
    // Hash table cleanup is handled in its destructor.
}

/**
 * Allocates a block of memory of the specified size using the mmap system call.
 * Rounds up the allocation size to the nearest page size to comply with mmap requirements.
 * Inserts the allocation details into the hash table for tracking.
 *
 * @param bytesToAllocate The number of bytes to allocate.
 * @return A pointer to the allocated memory block, or NULL if allocation fails.
 */
void* MyMalloc::allocate(size_t bytesToAllocate) {
    size_t pageSize = sysconf(_SC_PAGESIZE);
    size_t numPages = (bytesToAllocate + pageSize - 1) / pageSize; // Round up to the nearest page
    size_t allocationSize = numPages * pageSize;
    // Use mmap to allocate memory
    void* ptr = mmap(nullptr, allocationSize, PROT_READ | PROT_WRITE, MAP_PRIVATE | MAP_ANONYMOUS, -1, 0);
    assert(ptr != MAP_FAILED); // Simple error handling
    // Insert the allocation into the hash table
    hashTable.insert(ptr, allocationSize);
    return ptr;
}

/**
 * Deallocates a previously allocated block of memory.
 * Retrieves the size of the allocation from the hash table and then uses munmap to free it.
 * Upon successful deallocation, removes the allocation details from the hash table.
 *
 * @param ptr A pointer to the memory block to be deallocated.
 */
void MyMalloc::deallocate(void* ptr) {
    // Retrieve the allocation size from the hash table
    size_t allocationSize = hashTable.find(ptr);
    if (allocationSize > 0) {
        // Use munmap to deallocate memory, check result but don't assert here
        int result = munmap(ptr, allocationSize);
        if (result == 0) {
            // It's crucial to remove the entry from the hash table after successful deallocation
            hashTable.remove(ptr);
        } else {
            std::cerr << "Error: munmap failed for ptr: " << ptr << " with size: " << allocationSize << "\n";
            // Handle error or retry logic here
        }
}
}

