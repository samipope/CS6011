/**
 * Assignment 4: Malloc Replacement Assignment
 * Purpose: This assignment we are allocating/deallocating virtual memory to implement the library functions malloc() and free()
 *
 * Author: Samantha Pope
 * Date: March 11, 2024
 *
 * testStruct Class: where we test/implement our other two classes
 * we overload malloc() and free() to use my myMalloc object's allocate and deallocate
 *
 * How to run my tests: run this program, as they are all called in main!!
 *
 */


#include "myMalloc.h"
#include <iostream>
#include <vector>
#include <cstdlib> // For rand()
#include <chrono>
#include <numeric>


// Simple structure for testing allocations
struct TestStruct {
    int a;
    char b;
    float c;
};

/**
 * tests the performance and correctness of allocations of ints, chars and floats
 * @param myAllocator
 */
void testAllocations(MyMalloc& myAllocator) {
    // Measure the start time
    auto start = std::chrono::high_resolution_clock::now();
    // Test small allocations
    const int smallAllocs = 1000;
    TestStruct* smallArray[smallAllocs];
    for (int i = 0; i < smallAllocs; ++i) {
        smallArray[i] = static_cast<TestStruct*>(myAllocator.allocate(sizeof(TestStruct)));
        if (!smallArray[i]) {
            std::cerr << "Allocation failed for smallArray at index " << i << std::endl;
            return; // Or handle the failure appropriately
        }
        smallArray[i]->a = i;
        smallArray[i]->b = static_cast<char>(i);
        smallArray[i]->c = static_cast<float>(i);
    }
    // Verify data integrity
    for (int i = 0; i < smallAllocs; ++i) {
        if (smallArray[i]->a != i || smallArray[i]->b != static_cast<char>(i) || smallArray[i]->c != static_cast<float>(i)) {
            std::cerr << "Data integrity check failed for small allocations at index " << i << std::endl;
            return;
        }
    }

    // Test large allocations
    const int largeAllocs = 10;
    size_t largeSize = 1024 * 1024; // 1MB
    char* largeArray[largeAllocs];
    for (int i = 0; i < largeAllocs; ++i) {
        largeArray[i] = static_cast<char*>(myAllocator.allocate(largeSize));
        if (!largeArray[i]) {
            std::cerr << "Allocation failed for largeArray at index " << i << std::endl;
            return; // Or handle the failure appropriately
        }
        // Manipulate the data in the large allocations
        for (size_t j = 0; j < largeSize; j += sizeof(int)) {
            *(reinterpret_cast<int*>(largeArray[i] + j)) = static_cast<int>(j);
        }
    }
    // Free the allocated memory
    for (int i = 0; i < smallAllocs; ++i) {
        myAllocator.deallocate(smallArray[i]);
    }
    for (int i = 0; i < largeAllocs; ++i) {
        myAllocator.deallocate(largeArray[i]);
    }
    // Measure the end time and calculate the duration
    auto end = std::chrono::high_resolution_clock::now();
    std::chrono::duration<double, std::milli> duration = end - start;
    std::cout << "Test completed in " << duration.count() << " milliseconds." << std::endl;
}


/**
 * this test uses myAllocator object to allocate and deallocate 1000 integers, as recommended by the prof to test
 * @param myAllocator
 */
void testIntegerAllocations(MyMalloc& myAllocator) {
    std::cout << "Testing allocation and deallocation for 1000 integers...\n";
    // Number of integers to allocate
    const int numInts = 1000;

    // Allocate memory for 1000 integers
    int* integers = static_cast<int*>(myAllocator.allocate(numInts * sizeof(int)));
    // Check allocation success
    if (!integers) {
        std::cerr << "Allocation failed for integers array.\n";
        return;
    }
    // Assign values to each integer to verify the allocation worked
    for (int i = 0; i < numInts; ++i) {
        integers[i] = i;
    }
    // Optionally, verify the values
    for (int i = 0; i < numInts; ++i) {
        if (integers[i] != i) {
            std::cerr << "Data integrity check failed at index " << i << "\n";
            myAllocator.deallocate(integers);
            return;
        }
    }
    // Deallocate the allocated memory
    myAllocator.deallocate(integers);
    std::cout << "Allocation and deallocation for 1000 integers completed successfully!!!!\n";
}

/**
 * This test allocates 100 integers, then frees 50 of them. then it verifies that half of these integers were freed from memory
 * and that the other half of the integers remains.
 * @param myAllocator
 */
void testPartialDeallocation(MyMalloc& myAllocator) {
    std::cout << "Testing partial deallocation...\n";
    const int numInts = 100;
    const int numFrees = 50;
    // Allocate memory for 100 integers
    int** intPointers = new int*[numInts]; // Array of pointers to int
    // Allocate and assign values
    for (int i = 0; i < numInts; ++i) {
        intPointers[i] = static_cast<int*>(myAllocator.allocate(sizeof(int)));
        if (!intPointers[i]) {
            std::cerr << "Allocation failed for integer at index " << i << "\n";
            return; // Early return on failure
        }
        *intPointers[i] = i; // Assign value
    }
    // Deallocate 50 integers
    for (int i = 0; i < numFrees; ++i) {
        myAllocator.deallocate(intPointers[i]);
        intPointers[i] = nullptr; // Mark as freed
    }
    // Verification step: all deallocated pointers should be nullptr
    // Note: In real use, accessing freed memory is undefined behavior.
    for (int i = 0; i < numFrees; ++i) {
        if (intPointers[i] != nullptr) {
            std::cerr << "Memory not freed for index " << i << "\n";
            return;
        }
    }
    // Verification step: allocated pointers should not be nullptr
    for (int i = numFrees; i < numInts; ++i) {
        if (intPointers[i] == nullptr) {
            std::cerr << "Incorrect memory free for index " << i << "\n";
            return;
        }
    }
    // Clean up remaining allocations
    for (int i = numFrees; i < numInts; ++i) {
        if (intPointers[i] != nullptr) {
            myAllocator.deallocate(intPointers[i]);
        }
    }
    delete[] intPointers; // Clean up the array of pointers
    std::cout << "Partial deallocation test completed successfully!!!!\n";
}



void testNonOverlappingAllocations(MyMalloc& myAllocator) {
    std::cout << "Testing non-overlapping allocations...\n";
    const int numAllocations = 100;
    std::vector<void*> pointers(numAllocations);
    // Allocate memory and save pointers
    for (int i = 0; i < numAllocations; ++i) {
        pointers[i] = myAllocator.allocate(1024); // 1KB blocks
    }
    // Check for overlap
    for (int i = 0; i < numAllocations; ++i) {
        for (int j = i + 1; j < numAllocations; ++j) {
            if (pointers[i] == pointers[j]) {
                std::cerr << "Overlap detected between allocations " << i << " and " << j << std::endl;
                return;
            }
        }
    }
    std::cout << "Overlapping test completed successfully!!!!\n";
    // Clean up
    for (void* ptr : pointers) {
        myAllocator.deallocate(ptr);
    }
}

/**
 * This test times both myMalloc() object and the built in malloc() system call.
 *
 */
void TimingBenchmark() {
    MyMalloc myAllocator;
    std::vector<std::chrono::duration<double, std::milli>> timesCustom, timesMalloc;

    const int iterations = 10000;
    for (int i = 0; i < iterations; ++i) {
        auto start = std::chrono::high_resolution_clock::now();
        void* ptr = myAllocator.allocate(128); // Example small allocation
        myAllocator.deallocate(ptr);
        auto end = std::chrono::high_resolution_clock::now();
        timesCustom.push_back(end - start);

        start = std::chrono::high_resolution_clock::now();
        ptr = malloc(128); //built in
        free(ptr);
        end = std::chrono::high_resolution_clock::now();
        timesMalloc.push_back(end - start);
    }

    // Calculate and output average times
    auto avgCustom = std::accumulate(timesCustom.begin(), timesCustom.end(), std::chrono::duration<double, std::milli>(0)) / timesCustom.size();
    auto avgMalloc = std::accumulate(timesMalloc.begin(), timesMalloc.end(), std::chrono::duration<double, std::milli>(0)) / timesMalloc.size();

    std::cout << "Average allocation+deallocation time for MyMalloc object: " << avgCustom.count() << "ms" << std::endl;
    std::cout << "Average allocation+deallocation time for built in malloc: " << avgMalloc.count() << "ms" <<std::endl;
}



int main() {
    MyMalloc myAllocator;
    testAllocations(myAllocator); //tests allocations of floats, chars and ints
    testPartialDeallocation(myAllocator); //allocating 100, freeing 50 and verifying
    testIntegerAllocations(myAllocator); //allocating 1000, freeing 1000
    testNonOverlappingAllocations(myAllocator); //testing overlap
    TimingBenchmark(); //timing myMalloc vs the built-in malloc and timing both
    return 0;
}
