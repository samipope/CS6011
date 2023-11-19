package assignment05;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class SinglyLinkedList<T> implements List<T>{

    LinkNode<T> head;
    LinkNode<T> tail;
    private int size_;
    /**
     * The Default constructor
     */
    public SinglyLinkedList(){
        head = new LinkNode<>(null);
        size_ = 0;
    }

    /**
     * Inserts an element at the beginning of the list.
     * O(1) for a singly-linked list.
     *
     * @param element - the element to add
     */
    @Override
    public void insertFirst(T element) {
        //make head point to this element

        LinkNode<T> newNode = new LinkNode<>(element);

        newNode.next = head.next;
        head.next = newNode;
        size_++;
    }

    /**
     * Inserts an element at a specific position in the list.
     * O(N) for a singly-linked list.
     *
     * @param index - the specified position
     * @param element - the element to add
     * @throws IndexOutOfBoundsException if index is out of range (index < 0 || index > size())
     */
    @Override
    public void insert(int index, T element) throws IndexOutOfBoundsException {
        if(index < 0 || index > size_){
            throw new IndexOutOfBoundsException();
        }

        LinkNode<T> newNode = new LinkNode<>(element);
        LinkNode<T> n = head;

        for (int i = 0; i < index; i++){
            n = n.next;
        }
        newNode.next = n.next;
        n.next = newNode;
        size_++;

    }

    /**
     * Gets the first element in the list.
     * O(1) for a singly-linked list.
     *
     * @return the first element in the list
     * @throws NoSuchElementException if the list is empty
     */
    @Override
    public T getFirst() throws NoSuchElementException {
        if(isEmpty()){
            throw new NoSuchElementException();
        }
        return (T) head.next.data;
    }

    /**
     * Gets the element at a specific position in the list.
     * O(N) for a singly-linked list.
     *
     * @param index - the specified position
     * @return the element at the position
     * @throws IndexOutOfBoundsException if index is out of range (index < 0 || index >= size())
     */
    @Override
    public T get(int index) throws IndexOutOfBoundsException {
        if(index < 0 || index>=size_){
            throw new IndexOutOfBoundsException();
        }

        LinkNode<T> n = head;
        for(int i = 0; i <= index; i++){
            n = n.next;
        }

        return n.data;
    }

    /**
     * Deletes and returns the first element from the list.
     * O(1) for a singly-linked list.
     *
     * @return the first element
     * @throws NoSuchElementException if the list is empty
     */
    @Override
    public T deleteFirst() throws NoSuchElementException {
        if(isEmpty()){
            throw new NoSuchElementException();
        }
        T result = getFirst();

        head.next = head.next.next;
        size_--;
        return result;
    }

    /**
     * Deletes and returns the element at a specific position in the list.
     * O(N) for a singly-linked list.
     *
     * @param index - the specified position
     * @return the element at the position
     * @throws IndexOutOfBoundsException if index is out of range (index < 0 || index >= size())
     */
    @Override
    public T delete(int index) throws IndexOutOfBoundsException {
        if( index < 0 || index >= size_ || this.isEmpty()){
            throw new IndexOutOfBoundsException();
        }
        T result = get(index);
        LinkNode<T> n = head;

        for(int i = 0; i < index; i++){
            n = n.next;
        }

        n.next = n.next.next;

        size_--;
        return result;
    }

    /**
     * Determines the index of the first occurrence of the specified element in the list,
     * or -1 if this list does not contain the element.
     * O(N) for a singly-linked list.
     *
     * @param element - the element to search for
     * @return the index of the first occurrence; -1 if the element is not found
     */
    @Override
    public int indexOf(T element) {

        for (int i = 0; i < size_; i++) {
            T currentValue = get(i);

            if (currentValue.equals(element)) {
                return i;
            }
        }

        return -1; // Element not found
    }

    /**
     * O(1) for a singly-linked list.
     *
     * @return the number of elements in this list
     */
    @Override
    public int size() {
        return size_;
    }

    /**
     * O(1) for a singly-linked list.
     *
     * @return true if this collection contains no elements; false, otherwise
     */
    @Override
    public boolean isEmpty() {
        if(size_ == 0){
            return true;
        }
        return false;
    }

    /**
     * Removes all of the elements from this list.
     * O(1) for a singly-linked list.
     */
    @Override
    public void clear() {
        head.next = null;
        size_ = 0;
    }

    /**
     * Generates an array containing all of the elements in this list in proper sequence
     * (from first element to last element).
     * O(N) for a singly-linked list.
     *
     * @return an array containing all of the elements in this list, in order
     */
    @Override
    public T[] toArray() {
        T[] result = (T[])(new Object[size_]);
        LinkNode<T> n = head;

        for(int i = 0; i < size_; i++){
            result[i] = (T)n.next.data;
            n = n.next;
        }

        return result;
    }

    /**
     * @return an iterator over the elements in this list in proper sequence (from first
     * element to last element)
     */
    @Override
    public Iterator iterator() {
        return new myIterator();
    }

    public class LinkNode<T> {

        public T data;
        public LinkNode next;

        public LinkNode(T item) {

            data = item;
            next = null;

        }

    }


    private class myIterator implements Iterator<T>{
        LinkNode<T> currentNode;
        private boolean calledNext = false;

        public myIterator(){
            //make the current node the header (start at beginning)
            currentNode = head;
        }

        @Override
        public boolean hasNext() {
            //return if the next value is not null
            return currentNode.next != null;
        }

        @Override
        public T next() {
            if (!hasNext()) { //throw excpetion if the element does not exist
                throw new NoSuchElementException();
            }
            currentNode = currentNode.next;

            T data = currentNode.data;
            //set up the next node to hold the currentnode's data

            calledNext = true;
            return data;
        }

        @Override
        public void remove() {
            if(!calledNext){
                throw new IllegalStateException();
            }

            int currentIndex = indexOf(currentNode.data);
            delete(currentIndex);

            currentNode = head;
            for(int i = 0; i < currentIndex; i++){
                currentNode = currentNode.next;
            }

            calledNext = false;
        }
    }
}