/**
 * Assignment 4: Malloc Replacement Assignment
 * Purpose: This assignment we are allocating/deallocating virtual memory to implement the library functions malloc() and free()
 *
 * Author: Samantha Pope
 * Date: March 11, 2024
 *
 * hashTables Class: stores addresses and the associated size of memory allocated to that address
 * we can then hash the address to find the location to place the address/size into the hash table
 *
 */

#ifndef HASHTABLES_H
#define HASHTABLES_H

#include <cstddef> // for size_t

// Forward declaration of the HashEntry structure
struct HashEntry {
    void* key;   // Store the pointer as the key
    size_t size; // Size of the allocation
    bool inUse;  // Flag to check if entry is in use
};

class LinearProbingHashTable {
public:
    LinearProbingHashTable();
    ~LinearProbingHashTable();

    void insert(void* key, size_t size);
    void remove(void* key);
    size_t find(void* key);

private:
    HashEntry* table;
    size_t capacity;
    size_t used;

    size_t hash(void* ptr);
    void grow();
};

#endif // HASHTABLES_H
