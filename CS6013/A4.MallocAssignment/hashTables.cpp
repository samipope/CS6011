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

#include "hashTables.h"
#include "myMalloc.h"
#include <iostream>
#include <sys/mman.h>
#include <unistd.h>

const size_t HashTableSize = 1024; // Starting size of the hash table

/**
 * Constructor --> makes a LinearProbingHashTable object
 */
LinearProbingHashTable::LinearProbingHashTable() : capacity(HashTableSize), used(0) {
    table = static_cast<HashEntry*>(mmap(NULL, capacity * sizeof(HashEntry),
                                         PROT_READ | PROT_WRITE, MAP_ANON | MAP_PRIVATE, -1, 0));
    if (table == MAP_FAILED) {
        throw std::bad_alloc();
    }
    for (size_t i = 0; i < capacity; ++i) {
        table[i].inUse = false;
    }
}

/**
 * Destructor
 */
LinearProbingHashTable::~LinearProbingHashTable() {
    munmap(table, capacity * sizeof(HashEntry));
}

/**
 * hash - Calculates the hash value for a given pointer.
 *
 * @param ptr The pointer whose hash value is to be calculated.
 * @return The hash value for the given pointer.
 */
size_t LinearProbingHashTable::hash(void* ptr) {
    return (reinterpret_cast<size_t>(ptr) >> 12) % capacity;
    }

/**
 * grow - Doubles the size of the hash table and rehashes all entries.
 * Gets called when hash table exceeds threshold
 */
void LinearProbingHashTable::grow(){
        size_t newCapacity = capacity * 2;
        HashEntry* newTable = static_cast<HashEntry*>(mmap(NULL, newCapacity * sizeof(HashEntry),
                                                           PROT_READ | PROT_WRITE, MAP_ANON | MAP_PRIVATE, -1, 0));
        if (newTable == MAP_FAILED) {
            throw std::bad_alloc();
        }
        for (size_t i = 0; i < capacity; i++) {
            if (table[i].inUse) {
                size_t newIndex = hash(table[i].key);
                while (newTable[newIndex].inUse) {
                    newIndex = (newIndex + 1) % newCapacity;
                }
                newTable[newIndex] = table[i];
            }
        }
        munmap(table, capacity * sizeof(HashEntry));
        table = newTable;
        capacity = newCapacity;
    }

/**
 * insert - Inserts a new key-size pair into the hash table.
 * @param key The pointer to be stored as the key in the hash table.
 * @param size The size of the memory allocation associated with the key.
 */
void LinearProbingHashTable::insert(void* key, size_t size) {
        if (used >= capacity / 2) { // Load factor of 0.5
            grow();
        }
        size_t index = hash(key);
        while (table[index].inUse) {
            index = (index + 1) % capacity;
        }
        table[index] = {key, size, true};
        used++;
    }

/**
* remove - Removes an entry from the hash table based on the key.
* @param key The pointer that serves as the key for the entry to be removed.
*/
void LinearProbingHashTable::remove(void* key) {
        size_t index = hash(key);
        while (table[index].inUse) {
            if (table[index].key == key) {
                table[index].inUse = false; // Lazy deletion
                used--;
                return;
            }
            index = (index + 1) % capacity;
        }
    }


/**
 * find - Searches for an entry by key and returns its size.
 * @param key The pointer that serves as the key for the entry to be found.
 * @return The size associated with the key if found, or 0 if the key is not found.
 */
size_t LinearProbingHashTable::find(void* key){
        size_t index = hash(key);
        while (table[index].inUse) {
            if (table[index].key == key) {
                return table[index].size; // Found it, so return the size
            }
            index = (index + 1) % capacity;
        }
        return 0; // Not found - return 0
    }

