#include <iostream>

#include <thread>
#include <vector>
#include <atomic>
#include <chrono>
#include <mutex>
#include <omp.h>

/**
 * TO COMPILE RUN THE FOLLOWING COMMAND:
 *  /opt/homebrew/opt/llvm/bin/clang++ -fopenmp -std=c++11 -o parallel_reduction main.cpp -L/opt/homebrew/opt/llvm/lib -I/opt/homebrew/opt/llvm/include -I/opt/homebrew/opt/libomp/include
 */

/**
 * PART ONE:
 * @tparam T can pass in any type
 * @param array of numbers to do math with
 * @param N size of the array
 * @param numThreads number of threads doing work
 * @return
 */
template<typename T>
std::pair<T, std::chrono::milliseconds> parallelSumstd(T array[], size_t N, size_t numThreads){
    T sum = 0;
    std::vector<std::thread> threads;
    std::mutex mtx;

    auto startTime = std::chrono::steady_clock::now(); //start clock

    // make worker threads
    auto worker = [&] (size_t start, size_t end) {
        T partialSum = 0;
        for (size_t i = start; i < end; i++) {
            partialSum += array[i];
//            partialSum += (array[i] * array[i] - array[i]) / (array[i] + 1); //adding work
        }
        sum += partialSum;
    };

    size_t block_size = N / numThreads;
    for (size_t i = 0; i < numThreads; ++i) {
        size_t start = i * block_size;
        size_t end = (i == numThreads - 1) ? N : start + block_size;
        threads.push_back(std::thread(worker, start, end));
    }

    // join threads
    for (auto& th : threads) {
        if (th.joinable()) {
            th.join();
        }
    }

    auto end_time = std::chrono::steady_clock::now();
    std::chrono::milliseconds duration = std::chrono::duration_cast<std::chrono::milliseconds>(end_time - startTime);

    return {sum, duration};

}

/**
 * PART TWO:
 * @tparam T
 * @param array
 * @param N
 * @param numThreads
 * @return
 */
template<typename T>
std::pair<T, std::chrono::milliseconds> parallelSumOpenMP(T array[], size_t N, size_t numThreads){
    T sum = 0;

    omp_set_num_threads(numThreads);

    auto startTime = std::chrono::steady_clock::now(); //start clock

#pragma omp parallel
    {
        T localSum = 0; // Local sum for each thread
#pragma omp for nowait // Distribute the loop iterations across threads, no implicit barrier at end
        for (size_t i = 0; i < N; i++) {
            localSum += array[i];
        }

#pragma omp critical // Ensure one thread at a time updates the global sum
        sum += localSum;
    }


    auto endTime = std::chrono::steady_clock::now();
    std::chrono::milliseconds duration = std::chrono::duration_cast<std::chrono::milliseconds>(endTime - startTime);

    return {sum, duration};

}

/**
 * PART THREE:
 * @tparam T
 * @param array
 * @param N
 * @param numThreads
 * @return
 */

template<typename T>
std::pair<T, std::chrono::milliseconds> parallelSumOpenMPBuiltIn(T array[], size_t N, size_t numThreads){
    T sum = 0;  // change from std::atomic<T> to T

    omp_set_num_threads(numThreads);

    auto startTime = std::chrono::steady_clock::now(); // Start clock

#pragma omp parallel for reduction(+:sum)
    for (size_t i = 0; i < N; i++) {
        sum += array[i];
    }

    auto endTime = std::chrono::steady_clock::now();
    std::chrono::milliseconds duration = std::chrono::duration_cast<std::chrono::milliseconds>(endTime - startTime);

    return {sum, duration};
}



int main() {

    size_t N = 2560000000;
    float * a = new float [N];
    for(size_t i=0; i< N; i++){
        a[i]=i; //fill up array
    }
    size_t numThreads = 256;

    /**
   * PART ONE: make a timed test for using the STD C++ parallel reduction
   */
    auto resultPartOne = parallelSumstd(a,N,numThreads);
    std::cout << "[STD C++ BUILT IN] sum: " << resultPartOne.first << std::endl;
    std::cout << "[STD C++ BUILT IN] time it took: " << resultPartOne.second.count() << " ms" << std::endl;

    auto resultPartTwo = parallelSumOpenMP(a,N,numThreads);
    std::cout << "[OPENMP] sum: " << resultPartTwo.first << std::endl;
    std::cout << "[OPENMP] time it took: " << resultPartTwo.second.count() << " ms" << std::endl;

    auto resultPartThree = parallelSumOpenMPBuiltIn(a,N,numThreads);
    std::cout << "[OPENMP REDUCT] sum: " << resultPartThree.first << std::endl;
    std::cout << "[OPENMP REDUCT] time it took: " << resultPartThree.second.count() << " ms" << std::endl;


    delete[] a;
    return 0;
}
