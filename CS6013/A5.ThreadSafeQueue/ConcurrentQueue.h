////////////////////////////////////////////////////////////////////////
//
// Author: Samantha Pope
// Date: 03.29.2024
//
// CS 6013
//
// Concurrent Queue class: uses locks
//
////////////////////////////////////////////////////////////////////////

#pragma once
#include <mutex>

template <typename T>
class ConcurrentQueue {
public:
    ConcurrentQueue() : head_(new Node{T{}, nullptr}), size_(0) {
        tail_ = head_;
    }

    void enqueue(const T &x) {
        std::unique_lock<std::mutex> lock(mutex_); // locks the mutex to prevent race conditions
        Node* newNode = new Node{x, nullptr};
        if (size_ == 0) {
            head_ = newNode;
        } else {
            tail_->next = newNode;
        }
        tail_ = newNode;
        ++size_;
        // mutex automatically unlocks when lock goes out of scope
    }

    bool dequeue(T *ret) {
        std::unique_lock<std::mutex> lock(mutex_); // locks the mutex to prevent race conditions
        if (size_ == 0) {
            return false;
        }
        *ret = head_->data;
        Node* temp = head_;
        head_ = head_->next;
        delete temp;
        --size_;
        if (size_ == 0) {
            tail_ = nullptr;
        }
        // mutex automatically unlocks when lock goes out of scope
        return true;
    }

    ~ConcurrentQueue() {
        while (head_ != nullptr) {
            Node* temp = head_->next;
            delete head_;
            head_ = temp;
        }
    }
    int size() const { return size_; }
private:
    struct Node {
        T data;
        Node* next;
    };
    Node* head_;
    Node* tail_;
    int size_;
    mutable std::mutex mutex_; // mutex to protect against concurrent access
};
