#pragma once
////////////////////////////////////////////////////////////////////////
//
// Author: Samantha Pope
// Date: 03.29.2024
//
// CS 6013
//
// Outline for SerialQueue class. Fill in the missing data, comments, etc.
//
////////////////////////////////////////////////////////////////////////
template <typename T>
class SerialQueue {
public:
    SerialQueue() :
            head_( new Node{ T{}, nullptr } ), size_( 0 )
    {
        tail_ = head_;
    }
    void enqueue( const T & x ) {
        // Create a new node for the element to be enqueued
        Node* newNode = new Node{x, nullptr};
        // If the queue is empty, head_ will directly point to newNode
        if (size_ == 0) {
            head_ = newNode;
        } else {
            // Otherwise, link the current tail to the new node
            tail_->next = newNode;
        }
        // Update the tail pointer and size
        tail_ = newNode;
        ++size_;
    }
    bool dequeue( T * ret ) {
// Check if the queue is empty
        if (size_ == 0) {
            return false;
        }
        // Retrieve the data from the head node
        *ret = head_->data;
        // Move the head pointer to the next node
        Node* temp = head_;
        head_ = head_->next;
        // Delete the old head node
        delete temp;
        // Update size
        --size_;
        // If the queue becomes empty, reset the tail to null
        if (size_ == 0) {
            tail_ = nullptr;
        }

        return true;
    }
    ~SerialQueue() {
        while( head_ != nullptr ) {
            Node* temp = head_->next;
            delete head_;
            head_ = temp;
        }
    }
    int size() const { return size_; }
private:
    struct Node {
        T data;
        Node * next;
    };
    Node * head_;
    Node * tail_;
    int size_;
};
